package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.FinalizeOrderDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderItemRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.OrderResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.*;
import br.edu.utfpr.pb.pw44s.server.repository.AddressRepository;
import br.edu.utfpr.pb.pw44s.server.repository.OrderItemRepository;
import br.edu.utfpr.pb.pw44s.server.service.AuthService;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderService;
import br.edu.utfpr.pb.pw44s.server.service.UserService;
import br.edu.utfpr.pb.pw44s.server.service.impl.AddressServiceImpl;
import br.edu.utfpr.pb.pw44s.server.service.impl.ProductServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("orders")
public class OrderController extends CrudController<Order, OrderRequestDTO, OrderResponseDTO, OrderRequestDTO, Long> {
    private final IOrderService orderService;
    private final ModelMapper modelMapper;
    private final AddressServiceImpl addressService;
    private final AuthService authService;
    private final AddressRepository addressRepository;
    private final ProductServiceImpl productServiceImpl;
    private final OrderItemRepository orderItemRepository;

    public OrderController(IOrderService orderService, ModelMapper modelMapper, UserService userService, AddressServiceImpl addressService, AuthService authService, AddressRepository addressRepository, OrderItemRepository orderItemRepository, ProductServiceImpl productServiceImpl) {
        super(Order.class, OrderRequestDTO.class, OrderResponseDTO.class, OrderRequestDTO.class);
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
        this.authService = authService;
        this.addressRepository = addressRepository;
        this.productServiceImpl = productServiceImpl;
        this.orderItemRepository = orderItemRepository;
    }

    private User currentUserOrNull() {
        try {
            return authService.getAuthenticatedUser();
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<OrderResponseDTO> getCart() {
        User user = currentUserOrNull();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Non-authenticated users should use client-side cart (localStorage)");
        }

        Order existingCart = orderService.findCartForAuthenticatedUser();
        if(existingCart != null) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(existingCart));
        }

        Order cart = new Order();
        cart.setState(OrderState.CART);
        cart.setUser(user);
        cart.setDate(LocalDateTime.now());
        cart.setOrderItems(new ArrayList<>());

        Order saved = orderService.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(saved));
    }

    @PostMapping("/cart/add")
    @Transactional
    public ResponseEntity<OrderResponseDTO> addItem(@RequestBody @Valid OrderItemRequestDTO dto) {
        User user = currentUserOrNull();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Non-authenticated users should use client-side cart (localStorage)");
        }

        Order cart = orderService.findCartForAuthenticatedUser();
        if(cart == null) {
            cart = new Order();
            cart.setState(OrderState.CART);
            cart.setUser(user);
            cart.setDate(LocalDateTime.now());
            cart.setOrderItems(new ArrayList<>());
            Order saved = orderService.save(cart);
        }

        Product product = productServiceImpl.findById(dto.getProductId());

        Optional<OrderItem> existingItemOpt = cart.getOrderItems().stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst();

        if(existingItemOpt.isPresent()) {
            OrderItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + dto.getQuantity());
        } else {
            OrderItem item = new OrderItem();

            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setOrder(cart);
            cart.getOrderItems().add(item);
        }

        orderService.save(cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(cart));
    }

    @DeleteMapping("/cart/items/{itemId}")
    @Transactional
    public ResponseEntity<OrderResponseDTO> removeItem(@PathVariable Long itemId) {
        User user = currentUserOrNull();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Non-authenticated users should use client-side cart (localStorage)");
        }

        Order cart = orderService.findCartForAuthenticatedUser();

        OrderItem item = cart.getOrderItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Item not found in cart."
                ));

        cart.getOrderItems().remove(item);
        orderItemRepository.delete(item);

        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(cart));
    }

    @PostMapping("/cart/finalize")
    @Transactional
    public ResponseEntity<OrderResponseDTO> finalizeOrder(@Valid @RequestBody FinalizeOrderDTO dto) {
        User user = currentUserOrNull();

        Order cart = orderService.findCartForAuthenticatedUser();
        if(cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found.");
        }

        if(cart.getOrderItems().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty.");
        }

        Address address = addressService.findById(dto.getAddressId());
        if(!address.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        cart.setState(OrderState.ORDER);
        cart.setPaymentMethod(dto.getPaymentMethod());
        cart.setDeliveryMethod(dto.getDeliveryMethod());
        cart.setAddress(address);

        cart.setDate(LocalDateTime.now());

        orderService.save(cart);

        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(cart));
    }

    @Override
    @GetMapping //http://ip-api:port/request-mapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return ResponseEntity.ok(
                orderService.findAllForAuthenticatedUser()
                        .stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));
    }

    @Override
    protected ICrudService<Order, Long> getService() {
        return this.orderService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}
