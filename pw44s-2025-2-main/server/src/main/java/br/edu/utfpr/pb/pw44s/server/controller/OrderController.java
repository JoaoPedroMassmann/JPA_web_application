package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.requestdto.ProductRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.OrderResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.ProductResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderService;
import br.edu.utfpr.pb.pw44s.server.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("orders")
public class OrderController extends CrudController<Order, OrderRequestDTO, OrderResponseDTO, OrderRequestDTO, Long> {
    private final IOrderService orderService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public OrderController(IOrderService orderService, ModelMapper modelMapper, UserService userService) {
        super(Order.class, OrderRequestDTO.class, OrderResponseDTO.class, OrderRequestDTO.class);
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    protected Order convertToEntity(OrderRequestDTO entityDto) {
        User user = userService.findById(entityDto.getUser());
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        Order order = super.convertToEntity(entityDto);
        order.setUser(user);

        order.setDate(LocalDateTime.now());

        return order;
    }

    @Override
    protected OrderResponseDTO convertToDto(Order entity) {
        OrderResponseDTO orderDTO = super.convertToDto(entity);
        if(entity.getUser() != null) {
            orderDTO.setUserId(entity.getUser().getId());
            orderDTO.setUsername(entity.getUser().getUsername());
        }
        return orderDTO;
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
