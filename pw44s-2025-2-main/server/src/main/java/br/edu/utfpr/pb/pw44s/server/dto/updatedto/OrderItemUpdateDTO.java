package br.edu.utfpr.pb.pw44s.server.dto.updatedto;

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
public class OrderItemUpdateDTO {
    @DecimalMin(value = "0.01")
    private BigDecimal unitPrice;

    @Min(value = 1)
    private Integer quantity;
}
