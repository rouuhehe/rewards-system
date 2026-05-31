package com.example.restaurantservice.infrastructure.adapter.outbound.messaging;

public class TransactionEventSerializationException extends RuntimeException {

    public TransactionEventSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}