package com.example.restaurantservice.infrastructure.adapter.inbound.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        Double amount,
        String cardNumber,
        String restaurantCode,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateTime,
        String status,
        String message
) {}