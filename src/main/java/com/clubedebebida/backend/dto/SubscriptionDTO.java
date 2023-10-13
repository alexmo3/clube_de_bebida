package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
public record SubscriptionDTO(
    Long id,

    @NotBlank(message = "O nome deve ser informado")
    String name,

    String description,

    @NotBlank(message = "O cliente deve ser informado")
    Long userId,

    @NotBlank(message = "A garrafa deve ser informada")
    Long beverageId,

    @NotBlank(message = "A quantidade deve ser informada")
    int size,

    int balance,

    LocalDateTime createdAt,

    LocalDateTime updatedAt
) {
}
