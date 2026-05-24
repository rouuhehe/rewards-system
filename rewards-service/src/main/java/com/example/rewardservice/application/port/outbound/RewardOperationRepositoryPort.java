package com.example.rewardservice.application.port.outbound;

import com.example.rewardservice.domain.model.RewardOperation;

import java.util.List;

public interface RewardOperationRepositoryPort {
    RewardOperation save(RewardOperation rewardOperation);
    List<RewardOperation> findTop10ByCardNumberOrderByProcessedAtDesc(String cardNumber);
}