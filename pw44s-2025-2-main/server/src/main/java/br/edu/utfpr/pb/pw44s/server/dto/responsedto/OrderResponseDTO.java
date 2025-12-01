package br.edu.utfpr.pb.pw44s.server.dto.responsedto;

import br.edu.utfpr.pb.pw44s.server.model.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDTO {
    private Long id;

    private OrderState orderState;
    private String paymentMethod;
    private String deliveryMethod;
    private UserResponseDTO user;
    private LocalDateTime date;

    private AddressResponseDTO address;
    private List<OrderItemResponseDTO> orderItems;
}
