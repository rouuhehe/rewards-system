package com.example.restaurantservice.infrastructure.adapter.outbound.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.example.restaurantservice.domain.model.Transaction;
import com.example.restaurantservice.infrastructure.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class RabbitMQProducerAdapterTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void sendTransactionEvent_serializesAndPublishesJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        RabbitMQProducerAdapter adapter = new RabbitMQProducerAdapter(rabbitTemplate, objectMapper);
        Transaction transaction = new Transaction(UUID.randomUUID(), 120.5, "4111111111111111", "REST-001", LocalDateTime.parse("2026-05-24T10:15:30"), LocalDateTime.now());

        adapter.sendTransactionEvent(transaction);

        String expectedJson = objectMapper.writeValueAsString(transaction);
        verify(rabbitTemplate).convertAndSend(eq(RabbitMQConfig.QUEUE_NAME), eq(expectedJson));
    }

    @Test
    void sendTransactionEvent_wrapsSerializationFailure() throws Exception {
        ObjectMapper objectMapper = org.mockito.Mockito.mock(ObjectMapper.class);
        RabbitMQProducerAdapter adapter = new RabbitMQProducerAdapter(rabbitTemplate, objectMapper);
        Transaction transaction = new Transaction(120.5, "4111111111111111", "REST-001", LocalDateTime.parse("2026-05-24T10:15:30"));

        doThrow(new JsonProcessingException("boom") { }).when(objectMapper).writeValueAsString(transaction);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adapter.sendTransactionEvent(transaction));

        assertEquals("Error al convertir la transacción a JSON String", exception.getMessage());
        verifyNoInteractions(rabbitTemplate);
    }
}