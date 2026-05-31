package com.example.rewardservice.application.usecase;

import com.example.rewardservice.application.port.outbound.NotificationPort;
import com.example.rewardservice.application.port.outbound.RewardAccountRepositoryPort;
import com.example.rewardservice.application.port.outbound.RewardOperationRepositoryPort;
import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import com.example.rewardservice.domain.model.TransactionEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTransactionEventUseCaseTest {

    @Mock
    private RewardAccountRepositoryPort rewardAccountRepositoryPort;

    @Mock
    private RewardOperationRepositoryPort rewardOperationRepositoryPort;

    @Mock
    private NotificationPort notificationPort;

    @Test
    void execute_updatesAccountPersistsOperationAndNotifies() {
        ProcessTransactionEventUseCase useCase = new ProcessTransactionEventUseCase(
                rewardAccountRepositoryPort,
                rewardOperationRepositoryPort,
                notificationPort
        );

        TransactionEvent event = new TransactionEvent(
                UUID.randomUUID(),
                200.0,
                "4111111111111111",
                "REST-003",
                LocalDateTime.parse("2026-05-24T20:00:00"),
                LocalDateTime.now()
        );
        RewardAccount existing = new RewardAccount(UUID.randomUUID(), event.cardNumber(), 10.0, 2, event.dateTime(), LocalDateTime.now(), LocalDateTime.now());
        RewardAccount savedAccount = new RewardAccount(existing.getId(), existing.getCardNumber(), 20.0, 3, event.dateTime(), existing.getCreatedAt(), LocalDateTime.now());
        RewardOperation savedOperation = new RewardOperation(UUID.randomUUID(), event.id(), event.cardNumber(), event.restaurantCode(), event.amount(), 10.0, "PROCESSED", "Recompensa calculada exitosamente", LocalDateTime.now());

        when(rewardAccountRepositoryPort.findByCardNumber(event.cardNumber())).thenReturn(java.util.Optional.of(existing));
        when(rewardAccountRepositoryPort.save(any(RewardAccount.class))).thenReturn(savedAccount);
        when(rewardOperationRepositoryPort.save(any(RewardOperation.class))).thenReturn(savedOperation);

        RewardOperation result = useCase.execute(event);

        assertEquals(savedOperation, result);
        ArgumentCaptor<RewardAccount> accountCaptor = ArgumentCaptor.forClass(RewardAccount.class);
        verify(rewardAccountRepositoryPort).save(accountCaptor.capture());
        assertEquals(20.0, accountCaptor.getValue().getBalance());
        assertEquals(3, accountCaptor.getValue().getProcessedTransactions());
        verify(rewardOperationRepositoryPort).save(any(RewardOperation.class));
        verify(notificationPort).sendRewardNotification(savedAccount, savedOperation);
        verifyNoMoreInteractions(notificationPort);
    }
}
