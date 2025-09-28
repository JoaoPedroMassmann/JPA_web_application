package br.edu.utfpr.pb.pw44s.server.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {
    @NotNull
    private Long user;

    @NotBlank
    @Size(min = 2, max = 100)
    private String country;

    @Size(min = 2, max = 150)
    private String division;

    @Size(min = 2, max = 50)
    private String postalCode;

    @NotBlank
    @Size(min = 2, max = 200)
    private String city;

    @NotBlank
    @Size(min = 2, max = 200)
    private String street;

    @Size(max = 50)
    private String addressNumber;

    @NotBlank
    @Size(max = 50)
    private String addressType;
}