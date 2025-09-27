package br.edu.utfpr.pb.pw44s.server.dto.responsedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryResponseDTO {
    private Long id;
    private String name;
}
