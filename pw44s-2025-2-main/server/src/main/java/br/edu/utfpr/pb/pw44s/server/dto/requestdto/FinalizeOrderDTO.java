package br.edu.utfpr.pb.pw44s.server.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalizeOrderDTO {
    @NotBlank
    private String paymentMethod;

    @NotBlank
    String deliveryMethod;

    @NotNull
    private long addressId;
}
