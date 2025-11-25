package br.edu.utfpr.pb.pw44s.server.dto.responsedto;

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

    private String paymentMethod;
    private String deliveryMethod;

    // Buscar o usuário autenticado no SecurityContextHolder
    // Veio lá do AuthService getAuthenticatedUser()
    // private User user;

    // A data do pedido vai ficar no model apenas
    //private LocalDateTime datapedido
    private UserResponseDTO user;
    private LocalDateTime date;

    private AddressResponseDTO address;
    private List<OrderItemResponseDTO> orderItems;
}
