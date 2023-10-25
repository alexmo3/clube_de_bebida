package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DrinkDTO(
    Long id,

    @NotBlank(message = "O nome deve ser informado")
    String name,

    String description,

    @NotNull(message = "O tipo deve ser informado")
    int type,

    @NotBlank(message = "A unidade de medição deve ser informada")
    String unit,

    @NotNull(message = "O volume deve ser informado")
    int volume,

    int stock,

    int minStock,

    LocalDateTime createdAt,

    LocalDateTime updatedAt

) {
}