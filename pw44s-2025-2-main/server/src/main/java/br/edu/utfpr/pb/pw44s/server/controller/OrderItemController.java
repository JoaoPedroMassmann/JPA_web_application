package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderItemRequestDTO;
import br.edu.utfpr.pb.pw44s.server.model.OrderItem;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order_items")
public class OrderItemController extends CrudController<OrderItem, OrderItemRequestDTO, Long> {
    private final IOrderItemService orderItemService;
    private final ModelMapper modelMapper;

    public OrderItemController(IOrderItemService orderItemService, ModelMapper modelMapper) {
        super(OrderItem.class, OrderItemRequestDTO.class);
        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
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

