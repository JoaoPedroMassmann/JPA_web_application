package br.edu.utfpr.pb.pw44s.server.dto.responsedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {
    private Long id;

    private UserResponseDTO user;

    private String country;
    private String division;
    private String postalCode;
    private String city;
    private String street;
    private String addressNumber;
    private String addressType;
}