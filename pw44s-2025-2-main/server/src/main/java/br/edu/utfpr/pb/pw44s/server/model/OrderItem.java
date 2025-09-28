package br.edu.utfpr.pb.pw44s.server.model;

import br.edu.utfpr.pb.pw44s.server.ids.OrderItemId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_orderitem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @EmbeddedId
    private OrderItemId id = new OrderItemId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("order")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product")
    private Product product;

    private BigDecimal unitPrice;

    private Integer quantity;
}
