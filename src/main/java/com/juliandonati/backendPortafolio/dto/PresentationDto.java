package com.juliandonati.backendPortafolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PresentationDto {
    @Size(max=30,message = "El nombre de tu presentación no puede sobrepasar los 30 carácteres")
    @NotBlank(message = "Tu presentación debe tener un nombre")
    private String name;
    @Size(max=50, message="Tu título no puede sobrepasar los 50 carácteres. ¡Simplifícalo!")
    private String title;
    @Size(max=255, message="Tu descripción no puede sobrepasar los 255 carácteres")
    private String description;
    private String imgUrl;
}
