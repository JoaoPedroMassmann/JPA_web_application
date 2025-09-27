package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderRequestDTO;
import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.service.ICrudService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController extends CrudController<Order, OrderRequestDTO, Long> {
    private final IOrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(IOrderService orderService, ModelMapper modelMapper) {
        super(Order.class, OrderRequestDTO.class);
        this.orderService = orderService;
        this.modelMapper = modelMapper;
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
