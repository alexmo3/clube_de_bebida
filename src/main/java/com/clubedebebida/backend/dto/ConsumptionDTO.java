package com.clubedebebida.backend.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record ConsumptionDTO (
    Long id,

    @NotBlank(message = "A assinatura deve ser informada")
    Long subscriptionId,

    @NotBlank(message = "O total consumido deve ser informado")
    int total,

    @NotBlank(message = "O preço deve ser informado")
    float price,
    @NotBlank(message = "O garçom deve ser informado")
    int waiter,

    LocalDateTime createdAt,

    LocalDateTime updatedAt

){
}
