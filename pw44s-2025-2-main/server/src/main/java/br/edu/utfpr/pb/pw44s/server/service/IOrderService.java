package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.Order;

import java.util.List;

public interface IOrderService extends ICrudService<Order, Long> {
    public List<Order> findAllForAuthenticatedUser();
}
