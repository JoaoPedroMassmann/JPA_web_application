package br.edu.utfpr.pb.pw44s.server.dto.responsedto;

import br.edu.utfpr.pb.pw44s.server.dto.requestdto.OrderItemRequestDTO;
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
    private LocalDateTime date;
    private Long userId;
    private String username;
}
