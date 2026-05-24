package com.example.rewardservice.application.port.outbound;

import com.example.rewardservice.domain.model.RewardAccount;

import java.util.Optional;

public interface RewardAccountRepositoryPort {
    Optional<RewardAccount> findByCardNumber(String cardNumber);
    RewardAccount save(RewardAccount rewardAccount);
}