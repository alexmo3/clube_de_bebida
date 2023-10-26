package com.clubedebebida.backend.controller.request;

import com.clubedebebida.backend.model.Drink;
import com.clubedebebida.backend.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record SubscriptionRequest(
        Long id,

        @NotBlank(message = "O nome deve ser informado")
        String name,

        String description,

        Long userId,

        Long drinkId,

        @Min(value = 1L, message = "A quantidade deve ser informada")
        int size,

        int balance,

        int status
) {
}
