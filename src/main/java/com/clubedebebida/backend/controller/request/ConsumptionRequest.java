package com.clubedebebida.backend.controller.request;

import com.clubedebebida.backend.model.Subscription;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record ConsumptionRequest(
        Long id,

        @Min(value = 1L, message = "A assinatura deve ser informada")
        Long subscriptionId,

        @Min(value = 1, message = "O total consumido deve ser informado")
        int total,

        @DecimalMin(value = "0.00", inclusive = false, message = "O preço deve ser informado")
        BigDecimal price,

        @Min(value = 1, message = "O garçom deve ser informado")
        Long waiterId
) {
}
