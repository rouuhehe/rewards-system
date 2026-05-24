package com.example.rewardservice.infrastructure.adapter.inbound.messaging;

import com.example.rewardservice.application.usecase.ProcessTransactionEventUseCase;
import com.example.rewardservice.domain.model.TransactionEvent;
import com.example.rewardservice.infrastructure.config.RabbitMQConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventListener {

    private final ObjectMapper objectMapper;
    private final ProcessTransactionEventUseCase processTransactionEventUseCase;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleTransactionEvent(String payload) {
        try {
            TransactionEvent event = objectMapper.readValue(payload, TransactionEvent.class);
            processTransactionEventUseCase.execute(event);
        } catch (Exception e) {
            log.error("No se pudo procesar el evento de transacción: {}", payload, e);
            throw new IllegalStateException("Error procesando evento de transacción", e);
        }
    }
}