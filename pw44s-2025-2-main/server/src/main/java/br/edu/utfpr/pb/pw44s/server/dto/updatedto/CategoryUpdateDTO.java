package br.edu.utfpr.pb.pw44s.server.dto.updatedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryUpdateDTO {
    @Size(min = 3, max = 50)
    private String name;
}
