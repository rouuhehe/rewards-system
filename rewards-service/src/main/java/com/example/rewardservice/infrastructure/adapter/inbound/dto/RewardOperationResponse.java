package com.example.rewardservice.infrastructure.adapter.inbound.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RewardOperationResponse(
        UUID id,
        UUID transactionId,
        String cardNumber,
        String restaurantCode,
        Double amount,
        Double rewardAmount,
        String status,
        String message,
        LocalDateTime processedAt
) {
}