package com.example.restaurantservice.infrastructure.adapter.outbound.persistence;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.restaurantservice.domain.model.Transaction;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryAdapterTest {

    @Mock
    private TransactionJpaRepository transactionJpaRepository;

    @Test
    void save_delegatesToJpaRepository() {
        TransactionRepositoryAdapter adapter = new TransactionRepositoryAdapter(transactionJpaRepository);
        Transaction transaction = new Transaction(90.0, "4111111111111111", "REST-009", LocalDateTime.parse("2026-05-24T12:00:00"));
        Transaction saved = new Transaction(UUID.randomUUID(), 90.0, transaction.getCardNumber(), transaction.getRestaurantCode(), transaction.getDateTime(), LocalDateTime.now());
        when(transactionJpaRepository.save(any(Transaction.class))).thenReturn(saved);

        Transaction result = adapter.save(transaction);

        assertEquals(saved, result);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionJpaRepository).save(captor.capture());
        assertNull(captor.getValue().getId());
        assertEquals(transaction.getRestaurantCode(), captor.getValue().getRestaurantCode());
    }

    @Test
    void findById_returnsRepositoryResult() {
        TransactionRepositoryAdapter adapter = new TransactionRepositoryAdapter(transactionJpaRepository);
        UUID id = UUID.randomUUID();
        Transaction transaction = new Transaction(id, 90.0, "4111111111111111", "REST-009", LocalDateTime.parse("2026-05-24T12:00:00"), LocalDateTime.now());
        when(transactionJpaRepository.findById(id)).thenReturn(Optional.of(transaction));

        Transaction result = adapter.findById(id);

        assertEquals(transaction, result);
        verify(transactionJpaRepository).findById(id);
    }
}