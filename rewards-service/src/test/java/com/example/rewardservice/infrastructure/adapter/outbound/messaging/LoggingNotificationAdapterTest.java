package com.example.rewardservice.infrastructure.adapter.outbound.messaging;

import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class LoggingNotificationAdapterTest {

    @Test
    void sendRewardNotification_doesNotThrow() {
        LoggingNotificationAdapter adapter = new LoggingNotificationAdapter();
        RewardAccount account = new RewardAccount(UUID.randomUUID(), "4111111111111111", 15.0, 1, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        RewardOperation operation = new RewardOperation(UUID.randomUUID(), UUID.randomUUID(), account.getCardNumber(), "REST-004", 100.0, 5.0, "PROCESSED", "ok", LocalDateTime.now());

        adapter.sendRewardNotification(account, operation);
    }
}