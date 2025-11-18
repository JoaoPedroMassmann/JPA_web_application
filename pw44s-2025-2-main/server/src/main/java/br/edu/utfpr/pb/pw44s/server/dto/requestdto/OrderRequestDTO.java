package br.edu.utfpr.pb.pw44s.server.dto.requestdto;

import br.edu.utfpr.pb.pw44s.server.dto.responsedto.AddressResponseDTO;
import br.edu.utfpr.pb.pw44s.server.dto.responsedto.OrderResponseDTO;
import br.edu.utfpr.pb.pw44s.server.model.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestDTO {
    @NotBlank
    private String paymentMethod;

    @NotBlank
    private String deliveryMethod;

    // Buscar o usuário autenticado no SecurityContextHolder
    // Veio lá do AuthService getAuthenticatedUser()
    // private User user;

    // A data do pedido vai ficar no model apenas
    //private LocalDateTime datapedido

    @NotNull
    private Long addressId;

    @NotNull
    @Size(min = 1, message = "O pedido deve ter pelo menos 1 item.")
    private List<OrderItemRequestDTO> orderItems;
}