package com.juliandonati.backendPortafolio.security.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleRequestDto {
    @NotBlank(message = "El rol tiene que tener un nombre")
    @Size(max = 30, message = "El nombre del rol no puede exceder los 30 carácteres")
    private String name;

    @NotBlank(message = "El rol tiene que tener una descripción")
    private String description;
}
