package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record BeverageDTO(
    Long id,

    @NotBlank(message = "O nome deve ser informado")
    String name,

    String description,

    @NotBlank(message = "O tipo deve ser informado")
    int type,
    @NotBlank(message = "A unidade de medição deve ser informada")
    String unit,

    @NotBlank(message = "O volume deve ser informado")
    int volume,

    int stock,

    int minStock,

    LocalDateTime createdAt,

    LocalDateTime updatedAt

) {
}