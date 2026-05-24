package com.example.rewardservice.infrastructure.adapter.outbound.persistence;

import com.example.rewardservice.application.port.outbound.RewardOperationRepositoryPort;
import com.example.rewardservice.domain.model.RewardOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardOperationRepositoryAdapter implements RewardOperationRepositoryPort {

    private final RewardOperationJpaRepository rewardOperationJpaRepository;

    public RewardOperationRepositoryAdapter(RewardOperationJpaRepository rewardOperationJpaRepository) {
        this.rewardOperationJpaRepository = rewardOperationJpaRepository;
    }

    @Override
    public RewardOperation save(RewardOperation rewardOperation) {
        return rewardOperationJpaRepository.save(rewardOperation);
    }

    @Override
    public List<RewardOperation> findTop10ByCardNumberOrderByProcessedAtDesc(String cardNumber) {
        return rewardOperationJpaRepository.findTop10ByCardNumberOrderByProcessedAtDesc(cardNumber);
    }
}