package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPasswordDTO(
        Long id,

        @NotBlank(message = "E-mail deve ser informado")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha deve ser informada")
        String password,

        @NotBlank(message = "Nova senha deve ser informada")
        String newPassword
) {

}