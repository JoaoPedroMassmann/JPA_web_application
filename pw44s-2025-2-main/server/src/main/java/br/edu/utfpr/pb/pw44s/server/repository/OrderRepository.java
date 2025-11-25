package br.edu.utfpr.pb.pw44s.server.repository;

import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.model.OrderState;
import br.edu.utfpr.pb.pw44s.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.state = br.edu.utfpr.pb.pw44s.server.model.OrderState.CART")
    List<Order> findCartByUserId(Long userId);

}
