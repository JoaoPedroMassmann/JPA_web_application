package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderItemRequestDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.OrderItemResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.updatedto.OrderItemUpdateDTO;
import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.model.OrderItem;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderItemService;
import br.edu.utfpr.pb.pw44s.server.service.impl.OrderServiceImpl;
import br.edu.utfpr.pb.pw44s.server.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("order_items")
public class OrderItemController extends CrudController<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO, Long> {
    private final IOrderItemService orderItemService;
    private final ModelMapper modelMapper;
    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;

    public OrderItemController(IOrderItemService orderItemService, ModelMapper modelMapper, OrderServiceImpl orderService, ProductServiceImpl productService) {
        super(OrderItem.class, OrderItemRequestDTO.class, OrderItemResponseDTO.class);
        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    protected OrderItem convertToEntity(OrderItemRequestDTO entityDto) {
        OrderItem orderItem = super.convertToEntity(entityDto);

        Order order = (orderService.findById(entityDto.getOrder()));
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        Product product = (productService.findById(entityDto.getProduct()));
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        orderItem.setOrder(order);
        orderItem.setProduct(product);

        return orderItem;
    }

    @Override
    protected OrderItemResponseDTO convertToDto(OrderItem entity) {
        OrderItemResponseDTO orderItemDTO = super.convertToDto(entity);
        if(entity.getOrder() != null) {
            orderItemDTO.setOrderId(entity.getOrder().getId());
        } if(entity.getProduct() != null) {
            orderItemDTO.setProductId(entity.getProduct().getId());
            orderItemDTO.setProductName(entity.getProduct().getName());
        }
        return orderItemDTO;
    }



    @Override
    protected ICrudService<OrderItem, Long> getService() {
        return this.orderItemService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}

