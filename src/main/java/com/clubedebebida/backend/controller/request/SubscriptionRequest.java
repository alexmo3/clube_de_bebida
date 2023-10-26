package com.clubedebebida.backend.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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
