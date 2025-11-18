package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.ProductRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.OrderResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.ProductResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.*;
import br.edu.utfpr.pb.pw44s.server.repository.AddressRepository;
import br.edu.utfpr.pb.pw44s.server.repository.OrderItemRepository;
import br.edu.utfpr.pb.pw44s.server.service.AuthService;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderService;
import br.edu.utfpr.pb.pw44s.server.service.UserService;
import br.edu.utfpr.pb.pw44s.server.service.impl.AddressServiceImpl;
import br.edu.utfpr.pb.pw44s.server.service.impl.OrderItemServiceImpl;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("orders")
public class OrderController extends CrudController<Order, OrderRequestDTO, OrderResponseDTO, OrderRequestDTO, Long> {
    private final IOrderService orderService;
    private final ModelMapper modelMapper;
    private final AddressServiceImpl addressService;
    private final AuthService authService;
    private final OrderItemController orderItemController;
    private final AddressRepository addressRepository;

    public OrderController(IOrderService orderService, ModelMapper modelMapper, UserService userService, AddressServiceImpl addressService, AuthService authService, AddressRepository addressRepository, OrderItemController orderItemController, OrderItemRepository orderItemRepository) {
        super(Order.class, OrderRequestDTO.class, OrderResponseDTO.class, OrderRequestDTO.class);
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
        this.authService = authService;
        this.addressRepository = addressRepository;
        this.orderItemController = orderItemController;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(getService().save(convertToEntity(entity))));
    }

    public Order convertToEntity(@NotNull OrderRequestDTO dto) {
        User user = authService.getAuthenticatedUser();

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setDeliveryMethod(dto.getDeliveryMethod());
        order.setDate(LocalDateTime.now());

        Address address = addressService.findById(dto.getAddressId());

        order.setAddress(address);

        List<OrderItem> items = dto.getOrderItems().stream()
                .map(orderItemController::convertToEntity)
                .collect(Collectors.toCollection(ArrayList::new));

        order.setOrderItems(items);

        Order savedOrder = orderService.save(order);

        return savedOrder;
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
