package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTO(
        Long id,

        @NotBlank(message = "O nome deve ser informado")
        String name,

        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "O telefone deve ser informado")
        String phone,

        String photo,

        @NotBlank(message = "A data de nascimento deve ser informada")
        LocalDate birthday,

        @NotBlank(message = "A senha deve ser informada")
        String password,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {

}