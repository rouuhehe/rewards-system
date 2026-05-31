package com.example.rewardservice.application.dto;

import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import com.example.rewardservice.infrastructure.adapter.inbound.dto.RewardAccountResponse;
import com.example.rewardservice.infrastructure.adapter.inbound.dto.RewardOperationResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardDtosTest {

    @Test
    void rewardAccountDetails_exposesRecordValues() {
        RewardAccount account = new RewardAccount(UUID.randomUUID(), "4111111111111111", 15.0, 1, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        RewardOperation operation = new RewardOperation(UUID.randomUUID(), UUID.randomUUID(), account.getCardNumber(), "REST-004", 100.0, 5.0, "PROCESSED", "ok", LocalDateTime.now());
        RewardAccountDetails details = new RewardAccountDetails(account, List.of(operation));

        assertEquals(account, details.account());
        assertEquals(List.of(operation), details.operations());
    }

    @Test
    void rewardAccountResponse_exposesRecordValues() {
        UUID id = UUID.randomUUID();
        RewardAccountResponse response = new RewardAccountResponse(id, "4111111111111111", 15.0, 1, LocalDateTime.now(), List.of());

        assertEquals(id, response.id());
        assertEquals("4111111111111111", response.cardNumber());
    }

    @Test
    void rewardOperationResponse_exposesRecordValues() {
        UUID id = UUID.randomUUID();
        RewardOperationResponse response = new RewardOperationResponse(id, UUID.randomUUID(), "4111111111111111", "REST-004", 100.0, 5.0, "PROCESSED", "ok", LocalDateTime.now());

        assertEquals(id, response.id());
        assertEquals("PROCESSED", response.status());
    }
}