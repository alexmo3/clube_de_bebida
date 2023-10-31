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

        @Past
        @NotNull(message = "A data de nascimento deve ser informada")
        LocalDate birthday,

        @NotBlank(message = "A senha deve ser informada")
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        String password,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}