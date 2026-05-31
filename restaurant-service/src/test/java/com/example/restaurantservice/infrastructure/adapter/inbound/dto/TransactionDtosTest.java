package com.example.restaurantservice.infrastructure.adapter.inbound.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TransactionDtosTest {

    @Test
    void transactionRequest_exposesRecordValues() {
        LocalDateTime dateTime = LocalDateTime.parse("2026-05-24T18:00:00");
        TransactionRequest request = new TransactionRequest(75.5, "4111111111111111", "REST-002", dateTime);

        assertEquals(75.5, request.amount());
        assertEquals("4111111111111111", request.cardNumber());
        assertEquals("REST-002", request.restaurantCode());
        assertEquals(dateTime, request.dateTime());
    }

    @Test
    void transactionResponse_exposesRecordValues() {
        LocalDateTime dateTime = LocalDateTime.parse("2026-05-24T18:00:00");
        UUID id = UUID.randomUUID();
        TransactionResponse response = new TransactionResponse(id, 75.5, "4111111111111111", "REST-002", dateTime, "PUBLISHED", "ok");

        assertEquals(id, response.id());
        assertEquals("PUBLISHED", response.status());
        assertEquals("ok", response.message());
    }
}