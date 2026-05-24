package com.example.restaurantservice.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.restaurantservice.application.port.outbound.MessageProducerPort;
import com.example.restaurantservice.domain.model.Transaction;
import com.example.restaurantservice.domain.port.outbound.TransactionRepositoryPort;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegisterTransactionUseCase {

    private final MessageProducerPort messageProducerPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public RegisterTransactionUseCase(MessageProducerPort messageProducerPort,
                                      TransactionRepositoryPort transactionRepositoryPort) {
        this.messageProducerPort = messageProducerPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    public Transaction execute(Transaction transaction) {
        log.info("Iniciando registro de transacción: {}", transaction);

        Transaction entity = new Transaction(
                null,
                transaction.getAmount(),
                transaction.getCardNumber(),
                transaction.getRestaurantCode(),
                transaction.getDateTime(),
                LocalDateTime.now()
        );
        Transaction saved = transactionRepositoryPort.save(entity);
        log.info("Transacción guardada con ID: {}", saved.getId());

        messageProducerPort.sendTransactionEvent(saved);
        log.info("Evento de transacción enviado para ID: {}", saved.getId());

        return saved;
    }
}