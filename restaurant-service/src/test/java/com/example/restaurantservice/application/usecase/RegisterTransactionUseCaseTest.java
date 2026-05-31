package com.example.restaurantservice.application.usecase;

import com.example.restaurantservice.application.port.outbound.MessageProducerPort;
import com.example.restaurantservice.domain.model.Transaction;
import com.example.restaurantservice.domain.port.outbound.TransactionRepositoryPort;
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
class RegisterTransactionUseCaseTest {

    @Mock
    private MessageProducerPort messageProducerPort;

    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;

    @Test
    void execute_savesTransactionAndPublishesSavedEntity() {
        RegisterTransactionUseCase useCase = new RegisterTransactionUseCase(messageProducerPort, transactionRepositoryPort);
        Transaction input = new Transaction(120.0, "4111111111111111", "REST-001", LocalDateTime.parse("2026-05-24T10:15:30"));
        Transaction saved = new Transaction(UUID.randomUUID(), 120.0, "4111111111111111", "REST-001", input.getDateTime(), LocalDateTime.now());

        when(transactionRepositoryPort.save(any(Transaction.class))).thenReturn(saved);

        Transaction result = useCase.execute(input);

        assertEquals(saved, result);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepositoryPort).save(captor.capture());
        assertNull(captor.getValue().getId());
        assertEquals(input.getAmount(), captor.getValue().getAmount());
        verify(messageProducerPort).sendTransactionEvent(saved);
        verifyNoMoreInteractions(messageProducerPort, transactionRepositoryPort);
    }
}
