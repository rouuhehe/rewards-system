package com.example.rewardservice.infrastructure.adapter.outbound.messaging;

import com.example.rewardservice.application.port.outbound.NotificationPort;
import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggingNotificationAdapter implements NotificationPort {

    @Override
    public void sendRewardNotification(RewardAccount account, RewardOperation operation) {
        log.info(
                "Notificación de éxito para tarjeta {}: beneficio {} acumulado, saldo actual {}",
                account.getCardNumber(),
                operation.getRewardAmount(),
                account.getBalance()
        );
    }
}