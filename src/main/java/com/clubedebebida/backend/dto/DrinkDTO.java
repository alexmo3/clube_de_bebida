package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DrinkDTO(
    Long id,

    @NotBlank(message = "O nome deve ser informado")
    String name,

    String description,

    @NotNull(message = "O tipo deve ser informado")
    @Min(value = 1, message = "O tipo deve ser maior que zero")
    int type,

    @NotBlank(message = "A unidade de medição deve ser informada")
    @Min(value = 1, message = "A unidade de medição deve ser maior que zero")
    String unit,

    @NotNull(message = "O volume deve ser informado")
    @Min(value = 1, message = "O volume deve ser maior que zero")
    int volume,

    int stock,

    int minStock,

    LocalDateTime createdAt,

    LocalDateTime updatedAt

) {
}