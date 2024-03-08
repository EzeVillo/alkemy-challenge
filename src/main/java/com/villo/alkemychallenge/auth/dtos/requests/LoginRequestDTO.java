package com.villo.alkemychallenge.auth.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDTO {
    @NotNull(message = "El nombre de usuario no debe ser nulo")
    private String username;

    @NotNull(message = "La contraseña no debe ser nulo")
    private String password;
}
