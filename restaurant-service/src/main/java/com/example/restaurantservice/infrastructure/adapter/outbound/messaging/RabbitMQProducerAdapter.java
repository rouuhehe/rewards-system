package com.example.restaurantservice.infrastructure.adapter.outbound.messaging;

import com.example.restaurantservice.domain.model.Transaction;
import com.example.restaurantservice.infrastructure.config.RabbitMQConfig;
import com.example.restaurantservice.application.port.outbound.MessageProducerPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitMQProducerAdapter implements MessageProducerPort {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQProducerAdapter(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendTransactionEvent(Transaction transaction) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(transaction);
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, jsonMessage);
            log.info("Evento de transacción publicado en {} para id={}", RabbitMQConfig.QUEUE_NAME, transaction.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir la transacción a JSON String", e);
        }
    }
}
