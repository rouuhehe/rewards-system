package com.example.restaurantservice.application.port.outbound;

import com.example.restaurantservice.domain.model.Transaction;

public interface MessageProducerPort {
    void sendTransactionEvent(Transaction transaction);
}
