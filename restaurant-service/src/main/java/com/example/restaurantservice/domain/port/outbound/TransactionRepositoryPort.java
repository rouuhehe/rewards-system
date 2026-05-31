package com.example.restaurantservice.domain.port.outbound;

import java.util.UUID;

import com.example.restaurantservice.domain.model.Transaction;

public interface TransactionRepositoryPort {
    Transaction save(Transaction transaction);
    Transaction findById(UUID id);
}
