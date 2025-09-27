package br.edu.utfpr.pb.pw44s.server.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequestDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
}
