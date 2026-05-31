package com.example.rewardservice.application.usecase;

import com.example.rewardservice.application.port.outbound.RewardAccountRepositoryPort;
import com.example.rewardservice.application.port.outbound.RewardOperationRepositoryPort;
import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRewardAccountUseCaseTest {

    @Mock
    private RewardAccountRepositoryPort rewardAccountRepositoryPort;

    @Mock
    private RewardOperationRepositoryPort rewardOperationRepositoryPort;

    @Test
    void execute_returnsAccountAndHistoryWhenAccountExists() {
        GetRewardAccountUseCase useCase = new GetRewardAccountUseCase(rewardAccountRepositoryPort, rewardOperationRepositoryPort);
        RewardAccount account = new RewardAccount(UUID.randomUUID(), "4111111111111111", 15.0, 1, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        RewardOperation operation = new RewardOperation(UUID.randomUUID(), UUID.randomUUID(), account.getCardNumber(), "REST-001", 100.0, 5.0, "PROCESSED", "ok", LocalDateTime.now());

        when(rewardAccountRepositoryPort.findByCardNumber(account.getCardNumber())).thenReturn(java.util.Optional.of(account));
        when(rewardOperationRepositoryPort.findTop10ByCardNumberOrderByProcessedAtDesc(account.getCardNumber())).thenReturn(List.of(operation));

        var result = useCase.execute(account.getCardNumber());

        assertEquals(account, result.account());
        assertEquals(List.of(operation), result.operations());
    }

    @Test
    void execute_createsEmptyAccountWhenCardDoesNotExist() {
        GetRewardAccountUseCase useCase = new GetRewardAccountUseCase(rewardAccountRepositoryPort, rewardOperationRepositoryPort);
        String cardNumber = "4111111111111111";

        when(rewardAccountRepositoryPort.findByCardNumber(cardNumber)).thenReturn(java.util.Optional.empty());
        when(rewardOperationRepositoryPort.findTop10ByCardNumberOrderByProcessedAtDesc(cardNumber)).thenReturn(List.of());

        var result = useCase.execute(cardNumber);

        assertEquals(cardNumber, result.account().getCardNumber());
        assertEquals(0.0, result.account().getBalance());
        assertTrue(result.operations().isEmpty());
    }
}
