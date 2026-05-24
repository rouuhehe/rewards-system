package com.example.rewardservice.infrastructure.adapter.inbound.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RewardAccountResponse(
        UUID id,
        String cardNumber,
        Double balance,
        Integer processedTransactions,
        LocalDateTime lastTransactionAt,
        List<RewardOperationResponse> operations
) {
}