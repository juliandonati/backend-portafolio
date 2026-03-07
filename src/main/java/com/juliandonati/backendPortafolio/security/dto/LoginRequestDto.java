package com.juliandonati.backendPortafolio.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "El usuario/email no se puede dejar en blanco")
    private String usernameOrEmail;
    @NotBlank(message = "La contraseña no puede dejarse en blanco")
    private String unencryptedPassword;
}
