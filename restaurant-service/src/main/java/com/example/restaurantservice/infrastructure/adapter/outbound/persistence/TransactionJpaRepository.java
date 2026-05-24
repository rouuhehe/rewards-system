package com.example.restaurantservice.infrastructure.adapter.outbound.persistence;

import com.example.restaurantservice.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionJpaRepository extends JpaRepository<Transaction, UUID> {
}
