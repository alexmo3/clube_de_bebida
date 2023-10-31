package com.clubedebebida.backend.dto;

import com.clubedebebida.backend.model.Drink;
import com.clubedebebida.backend.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
public record SubscriptionDTO(
    Long id,

    //@NotBlank(message = "O nome deve ser informado")
    String name,

    String description,

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user,

    @ManyToOne
    @JoinColumn(name = "drink_id")
    Drink drink,

    int size,

    int balance,

    int status,

    LocalDateTime createdAt,

    LocalDateTime updatedAt
) {
}
