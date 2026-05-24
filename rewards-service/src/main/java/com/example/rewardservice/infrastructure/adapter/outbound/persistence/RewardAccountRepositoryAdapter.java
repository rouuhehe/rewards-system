package com.example.rewardservice.infrastructure.adapter.outbound.persistence;

import com.example.rewardservice.application.port.outbound.RewardAccountRepositoryPort;
import com.example.rewardservice.domain.model.RewardAccount;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RewardAccountRepositoryAdapter implements RewardAccountRepositoryPort {

    private final RewardAccountJpaRepository rewardAccountJpaRepository;

    public RewardAccountRepositoryAdapter(RewardAccountJpaRepository rewardAccountJpaRepository) {
        this.rewardAccountJpaRepository = rewardAccountJpaRepository;
    }

    @Override
    public Optional<RewardAccount> findByCardNumber(String cardNumber) {
        return rewardAccountJpaRepository.findByCardNumber(cardNumber);
    }

    @Override
    public RewardAccount save(RewardAccount rewardAccount) {
        return rewardAccountJpaRepository.save(rewardAccount);
    }
}