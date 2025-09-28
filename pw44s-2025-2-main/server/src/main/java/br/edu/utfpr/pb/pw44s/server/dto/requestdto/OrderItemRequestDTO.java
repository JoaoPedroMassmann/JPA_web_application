package br.edu.utfpr.pb.pw44s.server.dto.requestdto;

import br.edu.utfpr.pb.pw44s.server.model.Order;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemRequestDTO {
    @NotNull
    private Long order;

    @NotNull
    private Long product;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal unitPrice;

    @NotNull
    @Min(value = 1)
    private Integer quantity;
}
