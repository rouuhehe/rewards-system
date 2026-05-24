package com.example.restaurantservice.infrastructure.adapter.inbound.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TransactionRequest (
        @NotNull(message = "El monto no puede ser nulo")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
        Double amount,

        @NotEmpty(message = "El número de tarjeta no puede estar vacío")
        String cardNumber,

        @NotEmpty(message = "El código del restaurante no puede estar vacío")
        String restaurantCode,

        LocalDateTime dateTime
){}
