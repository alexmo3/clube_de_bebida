package com.clubedebebida.backend.controller.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record SaleRequest(
        Long id,

        @Min(value = 1, message = "Assinatura deve ser informada")
        Long subscriptionId,

        @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve informado")
        BigDecimal price,

        @Min(value = 1, message = "Garçom deve ser informado")
        Long waiterId
) {

}
