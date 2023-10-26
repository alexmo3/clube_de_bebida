package com.clubedebebida.backend.dto;

import com.clubedebebida.backend.model.Subscription;
import com.clubedebebida.backend.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsumptionDTO (
    Long id,

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    //@Min(value = 1L, message = "A assinatura deve ser informada")
    Subscription subscription,

    //@Min(value = 1, message = "O total consumido deve ser informado")
    int total,

    //@DecimalMin(value = "0.00", inclusive = false, message = "O preço deve ser informado")
    BigDecimal price,

    @ManyToOne
    @JoinColumn(name = "waiter_id")
    //@Min(value = 1, message = "O garçom deve ser informado")
    User waiter,

    LocalDateTime createdAt,

    LocalDateTime updatedAt

){
}
