package com.clubedebebida.backend.dto;

import com.clubedebebida.backend.model.Drink;
import com.clubedebebida.backend.model.User;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
public record SubscriptionDTO(
    Long id,

    @NotBlank(message = "O nome deve ser informado")
    String name,

    String description,

    @ManyToOne
    @Min(value = 1L, message = "O cliente deve ser informado")
    User user,

    @ManyToOne
    @Min(value = 1L, message = "A garrafa deve ser informada")
    Drink drink,

    @Min(value = 1L, message = "A quantidade deve ser informada")
    int size,

    int balance,

    int status,

    LocalDateTime createdAt,

    LocalDateTime updatedAt
) {
}
