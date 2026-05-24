package com.example.rewardservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionEvent(
        UUID id,
        Double amount,
        String cardNumber,
        String restaurantCode,
        LocalDateTime dateTime,
        LocalDateTime createdAt
) {
}