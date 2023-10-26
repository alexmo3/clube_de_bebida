package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTO(
        Long id,

        @Min(value = 1, message = "O tipo deve ser informado")
        int type,

        @NotBlank(message = "O nome deve ser informado")
        String name,

        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "O telefone deve ser informado")
        String phone,

        String photo,

        //@Past
        //@NotBlank(message = "A data de nascimento deve ser informada")
        LocalDate birthday,

        @NotBlank(message = "A senha deve ser informada")
        @Min(value = 6, message = "A senha deve ter no mínimo 6 caracteres")
        @Min(value = 20, message = "A senha deve ter no máximo 20 caracteres")
        String password,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}