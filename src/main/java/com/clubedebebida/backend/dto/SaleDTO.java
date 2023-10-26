package com.clubedebebida.backend.dto;

import com.clubedebebida.backend.model.Subscription;
import com.clubedebebida.backend.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleDTO (
        Long id,

        @ManyToOne
        @JoinColumn(name = "subscription_id")
        //@Min(value = 1, message = "Assinatura deve ser informada")
        Subscription subscription,

        @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve informado")
        BigDecimal price,

        @ManyToOne
        @JoinColumn(name = "waiter_id")
        //@Min(value = 1, message = "Garçom deve ser informado")
        User waiter,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
){
}
