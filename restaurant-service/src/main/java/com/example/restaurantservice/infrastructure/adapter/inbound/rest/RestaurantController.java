package com.example.restaurantservice.infrastructure.adapter.inbound.rest;

import com.example.restaurantservice.application.usecase.RegisterTransactionUseCase;
import com.example.restaurantservice.domain.model.Transaction;
import com.example.restaurantservice.infrastructure.adapter.inbound.dto.TransactionRequest;
import com.example.restaurantservice.infrastructure.adapter.inbound.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
@Slf4j
public class RestaurantController {

    private final RegisterTransactionUseCase registerTransactionUseCase;

    public RestaurantController(RegisterTransactionUseCase registerTransactionUseCase) {
        this.registerTransactionUseCase = registerTransactionUseCase;
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponse> registerCena(@Valid @RequestBody TransactionRequest request) {
        log.info("Recibida solicitud de transacción para restaurante: {}", request.restaurantCode());

        Transaction transaction = new Transaction(
                request.amount(),
                request.cardNumber(),
                request.restaurantCode(),
                request.dateTime() != null ? request.dateTime() : java.time.LocalDateTime.now()
        );

        Transaction saved = registerTransactionUseCase.execute(transaction);

        TransactionResponse response = new TransactionResponse(
                saved.getId(),
                saved.getAmount(),
                saved.getCardNumber(),
                saved.getRestaurantCode(),
                saved.getDateTime(),
                "PUBLISHED",
                "Transacción recibida y evento de cena publicado exitosamente."
        );

        log.info("Respuesta enviada para transacción ID: {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}