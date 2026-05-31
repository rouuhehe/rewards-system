package com.example.restaurantservice.infrastructure.config;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RabbitMQConfigTest {

    @Test
    void queue_isDurableAndUsesExpectedName() {
        RabbitMQConfig config = new RabbitMQConfig();

        assertEquals(RabbitMQConfig.QUEUE_NAME, config.queue().getName());
        assertTrue(config.queue().isDurable());
    }

    @Test
    void objectMapper_serializesLocalDateTime() {
        RabbitMQConfig config = new RabbitMQConfig();

        assertDoesNotThrow(() -> config.objectMapper().writeValueAsString(LocalDateTime.parse("2026-05-24T10:15:30")));
    }
}