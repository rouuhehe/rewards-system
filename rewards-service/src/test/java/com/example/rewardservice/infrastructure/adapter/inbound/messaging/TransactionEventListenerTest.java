package com.example.rewardservice.infrastructure.adapter.inbound.messaging;

import com.example.rewardservice.application.usecase.ProcessTransactionEventUseCase;
import com.example.rewardservice.domain.model.TransactionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionEventListenerTest {

    @Mock
    private ProcessTransactionEventUseCase processTransactionEventUseCase;

    @Test
    void handleTransactionEvent_deserializesPayloadAndDelegates() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        TransactionEventListener listener = new TransactionEventListener(objectMapper, processTransactionEventUseCase);
        TransactionEvent event = new TransactionEvent(UUID.randomUUID(), 100.0, "4111111111111111", "REST-005", LocalDateTime.parse("2026-05-24T12:00:00"), LocalDateTime.now());
        String payload = objectMapper.writeValueAsString(event);

        listener.handleTransactionEvent(payload);

        verify(processTransactionEventUseCase).execute(event);
    }
}
