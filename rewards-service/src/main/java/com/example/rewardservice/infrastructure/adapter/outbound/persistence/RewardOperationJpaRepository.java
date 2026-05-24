package com.example.rewardservice.infrastructure.adapter.outbound.persistence;

import com.example.rewardservice.domain.model.RewardOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RewardOperationJpaRepository extends JpaRepository<RewardOperation, UUID> {
    List<RewardOperation> findTop10ByCardNumberOrderByProcessedAtDesc(String cardNumber);
}