package com.example.restaurantservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String restaurantCode;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Transaction(Double amount, String cardNumber, String restaurantCode, LocalDateTime dateTime) {
        this(null, amount, cardNumber, restaurantCode, dateTime, null);
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}