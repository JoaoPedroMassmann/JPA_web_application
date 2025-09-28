package br.edu.utfpr.pb.pw44s.server.dto.updatedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDTO {
    @Size(min = 2, max = 100)
    private String country;

    @Size(min = 2, max = 150)
    private String division;

    @Size(min = 2, max = 50)
    private String postalCode;

    @Size(min = 2, max = 200)
    private String city;

    @Size(min = 2, max = 200)
    private String street;

    @Size(max = 50)
    private String addressNumber;

    @Size(max = 50)
    private String addressType;
}