package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record SaleDTO (
        Long id,

        @NotBlank(message = "A assinatura deve ser informada")
        Long subscriptionId,

        @NotBlank(message = "O preço deve ser informado")
        float price,

        @NotBlank(message = "O garçom deve ser informado")
        int waiter,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
){
}
