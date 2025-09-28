package br.edu.utfpr.pb.pw44s.server.dto.requestdto;

import br.edu.utfpr.pb.pw44s.server.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Long category;
}
