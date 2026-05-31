package com.example.restaurantservice.infrastructure.adapter.inbound.rest;

import com.example.restaurantservice.application.usecase.RegisterTransactionUseCase;
import com.example.restaurantservice.domain.model.Transaction;
import com.example.restaurantservice.infrastructure.adapter.inbound.dto.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RegisterTransactionUseCase registerTransactionUseCase;

    @Test
    void registerCena_returnsCreatedResponseWithPublishedStatus() {
        RestaurantController controller = new RestaurantController(registerTransactionUseCase);
        TransactionRequest request = new TransactionRequest(95.5, "4111111111111111", "REST-002", LocalDateTime.parse("2026-05-24T18:00:00"));
        Transaction saved = new Transaction(UUID.randomUUID(), 95.5, "4111111111111111", "REST-002", request.dateTime(), LocalDateTime.now());

        when(registerTransactionUseCase.execute(org.mockito.ArgumentMatchers.any(Transaction.class))).thenReturn(saved);

        var response = controller.registerCena(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved.getId(), response.getBody().id());
        assertEquals("PUBLISHED", response.getBody().status());
    }
}
