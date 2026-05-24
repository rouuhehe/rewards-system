package com.example.rewardservice.application.usecase;

import com.example.rewardservice.application.port.outbound.NotificationPort;
import com.example.rewardservice.application.port.outbound.RewardAccountRepositoryPort;
import com.example.rewardservice.application.port.outbound.RewardOperationRepositoryPort;
import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import com.example.rewardservice.domain.model.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessTransactionEventUseCase {

    private static final double REWARD_PERCENTAGE = 0.05;

    private final RewardAccountRepositoryPort rewardAccountRepositoryPort;
    private final RewardOperationRepositoryPort rewardOperationRepositoryPort;
    private final NotificationPort notificationPort;

    @Transactional
    public RewardOperation execute(TransactionEvent event) {
        if (event.amount() == null || event.amount() <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser mayor a 0");
        }

        RewardAccount account = rewardAccountRepositoryPort.findByCardNumber(event.cardNumber())
                .orElseGet(() -> new RewardAccount(event.cardNumber()));

        double rewardAmount = roundToTwoDecimals(event.amount() * REWARD_PERCENTAGE);
        LocalDateTime processedAt = LocalDateTime.now();

        account.setBalance(roundToTwoDecimals(account.getBalance() + rewardAmount));
        account.setProcessedTransactions(account.getProcessedTransactions() + 1);
        account.setLastTransactionAt(event.dateTime() != null ? event.dateTime() : processedAt);

        RewardAccount savedAccount = rewardAccountRepositoryPort.save(account);

        RewardOperation operation = new RewardOperation(
                null,
                event.id(),
                event.cardNumber(),
                event.restaurantCode(),
                event.amount(),
                rewardAmount,
                "PROCESSED",
                "Recompensa calculada exitosamente",
                processedAt
        );

        RewardOperation savedOperation = rewardOperationRepositoryPort.save(operation);
        notificationPort.sendRewardNotification(savedAccount, savedOperation);

        log.info("Evento procesado para tarjeta {} con beneficio {}", event.cardNumber(), rewardAmount);
        return savedOperation;
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0d) / 100.0d;
    }
}