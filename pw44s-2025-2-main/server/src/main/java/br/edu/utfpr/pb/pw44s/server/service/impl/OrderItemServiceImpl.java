package br.edu.utfpr.pb.pw44s.server.service.impl;

import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.model.OrderItem;
import br.edu.utfpr.pb.pw44s.server.model.OrderState;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.OrderItemRepository;
import br.edu.utfpr.pb.pw44s.server.repository.OrderRepository;
import br.edu.utfpr.pb.pw44s.server.service.AuthService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderItemService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderItemServiceImpl extends CrudServiceImpl<OrderItem, Long> implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, AuthService authService, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.authService = authService;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void updateQuantity(Long orderItemId, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than 0");
        }

        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "OrderItem not found"));

        Order order = item.getOrder();

        if (order == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "OrderItem is not linked to an order");
        }

        if (order.getState() != OrderState.CART) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Cannot edit items of a finalized order");
        }

        if (order.getUser() != null) {
            var user = authService.getAuthenticatedUser();
            if (!order.getUser().getId().equals(user.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "This item does not belong to the authenticated user");
            }
        }

        item.setQuantity(quantity);
        orderItemRepository.save(item);
    }

    @Override
    protected JpaRepository<OrderItem, Long> getRepository() {
        return orderItemRepository;
    }
}