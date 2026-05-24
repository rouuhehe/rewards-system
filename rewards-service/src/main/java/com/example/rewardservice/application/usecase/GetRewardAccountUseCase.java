package com.example.rewardservice.application.usecase;

import org.springframework.stereotype.Service;

import com.example.rewardservice.application.dto.RewardAccountDetails;
import com.example.rewardservice.application.port.outbound.RewardAccountRepositoryPort;
import com.example.rewardservice.application.port.outbound.RewardOperationRepositoryPort;
import com.example.rewardservice.domain.model.RewardAccount;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetRewardAccountUseCase {

    private final RewardAccountRepositoryPort rewardAccountRepositoryPort;
    private final RewardOperationRepositoryPort rewardOperationRepositoryPort;

    public RewardAccountDetails execute(String cardNumber) {
        RewardAccount account = rewardAccountRepositoryPort.findByCardNumber(cardNumber)
            .orElseGet(() -> new RewardAccount(cardNumber));
        return new RewardAccountDetails(
                account,
                rewardOperationRepositoryPort.findTop10ByCardNumberOrderByProcessedAtDesc(cardNumber)
        );
    }
}