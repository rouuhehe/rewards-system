package com.example.rewardservice.infrastructure.adapter.outbound.persistence;

import com.example.rewardservice.domain.model.RewardAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RewardAccountJpaRepository extends JpaRepository<RewardAccount, UUID> {
    Optional<RewardAccount> findByCardNumber(String cardNumber);
}