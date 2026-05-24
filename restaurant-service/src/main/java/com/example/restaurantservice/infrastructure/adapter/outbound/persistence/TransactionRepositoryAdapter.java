package com.example.restaurantservice.infrastructure.adapter.outbound.persistence;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.restaurantservice.domain.model.Transaction;
import com.example.restaurantservice.domain.port.outbound.TransactionRepositoryPort;

@Component
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;

    public TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return jpaRepository.save(transaction);
    }

    @Override
    public Transaction findById(UUID id) {
        return jpaRepository.findById(id).orElse(null);
    }
}