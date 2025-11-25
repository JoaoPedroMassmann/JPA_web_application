package br.edu.utfpr.pb.pw44s.server.service.impl;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderItemRequestDTO;
import br.edu.utfpr.pb.pw44s.server.model.*;
import br.edu.utfpr.pb.pw44s.server.repository.AddressRepository;
import br.edu.utfpr.pb.pw44s.server.repository.OrderRepository;
import br.edu.utfpr.pb.pw44s.server.service.AuthService;
import br.edu.utfpr.pb.pw44s.server.service.IOrderService;
import br.edu.utfpr.pb.pw44s.server.service.IProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, Long> implements IOrderService {
    private final OrderRepository orderRepository;
    private final IProductService productService;
    private final AddressRepository addressRepository;
    private AuthService authService;

    public OrderServiceImpl(OrderRepository orderRepository, IProductService productService, AuthService authService, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.authService = authService;
        this.addressRepository = addressRepository;
    }


    private OrderItem convertToOrderItem(OrderItemRequestDTO dto, Order order) {
        Product product = productService.findById(dto.getProductId());

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());

        orderItem.setUnitPrice(product.getPrice());

        return orderItem;
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    public List<Order> findAllForAuthenticatedUser() {
        User user = authService.getAuthenticatedUser();
        return orderRepository.findByUser(user);
    }

    /*
public class PedidoServiceImpl
   private Pedido save(Pedido pedido) {
      pedido.setUser(authServicer.getAuthenticatedUser())
   }
}
    fazer: post e get, sem put ou delete.

 */

}