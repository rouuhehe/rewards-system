package com.example.rewardservice.infrastructure.adapter.outbound.persistence;

import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardRepositoryAdaptersTest {

    @Mock
    private RewardAccountJpaRepository rewardAccountJpaRepository;

    @Mock
    private RewardOperationJpaRepository rewardOperationJpaRepository;

    @Test
    void rewardAccountAdapter_delegatesSaveAndFind() {
        RewardAccountRepositoryAdapter adapter = new RewardAccountRepositoryAdapter(rewardAccountJpaRepository);
        RewardAccount account = new RewardAccount("4111111111111111");
        RewardAccount saved = new RewardAccount(UUID.randomUUID(), account.getCardNumber(), 10.0, 2, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        when(rewardAccountJpaRepository.save(any(RewardAccount.class))).thenReturn(saved);
        when(rewardAccountJpaRepository.findByCardNumber(account.getCardNumber())).thenReturn(Optional.of(saved));

        RewardAccount savedResult = adapter.save(account);
        Optional<RewardAccount> foundResult = adapter.findByCardNumber(account.getCardNumber());

        assertEquals(saved, savedResult);
        assertEquals(Optional.of(saved), foundResult);
        ArgumentCaptor<RewardAccount> captor = ArgumentCaptor.forClass(RewardAccount.class);
        verify(rewardAccountJpaRepository).save(captor.capture());
        assertNull(captor.getValue().getId());
    }

    @Test
    void rewardOperationAdapter_delegatesSaveAndHistoryLookup() {
        RewardOperationRepositoryAdapter adapter = new RewardOperationRepositoryAdapter(rewardOperationJpaRepository);
        RewardOperation operation = new RewardOperation(null, UUID.randomUUID(), "4111111111111111", "REST-004", 100.0, 5.0, "PROCESSED", "ok", LocalDateTime.now());
        RewardOperation saved = new RewardOperation(UUID.randomUUID(), operation.getTransactionId(), operation.getCardNumber(), operation.getRestaurantCode(), operation.getAmount(), operation.getRewardAmount(), operation.getStatus(), operation.getMessage(), operation.getProcessedAt());
        when(rewardOperationJpaRepository.save(any(RewardOperation.class))).thenReturn(saved);
        when(rewardOperationJpaRepository.findTop10ByCardNumberOrderByProcessedAtDesc(operation.getCardNumber())).thenReturn(List.of(saved));

        RewardOperation savedResult = adapter.save(operation);
        List<RewardOperation> history = adapter.findTop10ByCardNumberOrderByProcessedAtDesc(operation.getCardNumber());

        assertEquals(saved, savedResult);
        assertEquals(List.of(saved), history);
    }
}