package br.edu.utfpr.pb.pw44s.server.dto;

import br.edu.utfpr.pb.pw44s.server.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO{
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 2, max = 200)
    private String country;

    @NotNull
    @Size(min = 2, max = 200)
    private String division;

    @NotNull
    private String postalCode;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private double number;

    @NotNull
    private String addressType;
}