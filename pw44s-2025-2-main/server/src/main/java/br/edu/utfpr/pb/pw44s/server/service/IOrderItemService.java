package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.OrderItem;

public interface IOrderItemService extends ICrudService<OrderItem, Long> {
    void updateQuantity(Long orderItemId, Integer quantity);
}
