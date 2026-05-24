package com.example.rewardservice.infrastructure.adapter.inbound.rest;

import com.example.rewardservice.application.dto.RewardAccountDetails;
import com.example.rewardservice.application.usecase.GetRewardAccountUseCase;
import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import com.example.rewardservice.infrastructure.adapter.inbound.dto.RewardAccountResponse;
import com.example.rewardservice.infrastructure.adapter.inbound.dto.RewardOperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards/accounts")
@RequiredArgsConstructor
public class RewardAccountController {

    private final GetRewardAccountUseCase getRewardAccountUseCase;

    @GetMapping("/{cardNumber}")
    public ResponseEntity<RewardAccountResponse> getAccount(@PathVariable String cardNumber) {
        RewardAccountDetails details = getRewardAccountUseCase.execute(cardNumber);
        RewardAccount account = details.account();

        List<RewardOperationResponse> operations = details.operations().stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(new RewardAccountResponse(
                account.getId(),
                account.getCardNumber(),
                account.getBalance(),
                account.getProcessedTransactions(),
                account.getLastTransactionAt(),
                operations
        ));
    }

    private RewardOperationResponse toResponse(RewardOperation operation) {
        return new RewardOperationResponse(
                operation.getId(),
                operation.getTransactionId(),
                operation.getCardNumber(),
                operation.getRestaurantCode(),
                operation.getAmount(),
                operation.getRewardAmount(),
                operation.getStatus(),
                operation.getMessage(),
                operation.getProcessedAt()
        );
    }
}