package com.example.rewardservice.infrastructure.adapter.inbound.rest;

import com.example.rewardservice.application.dto.RewardAccountDetails;
import com.example.rewardservice.application.usecase.GetRewardAccountUseCase;
import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardAccountControllerTest {

    @Mock
    private GetRewardAccountUseCase getRewardAccountUseCase;

    @Test
    void getAccount_returnsCurrentStateForCardNumber() {
        RewardAccountController controller = new RewardAccountController(getRewardAccountUseCase);
        RewardAccount account = new RewardAccount(UUID.randomUUID(), "4111111111111111", 15.0, 1, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        RewardOperation operation = new RewardOperation(UUID.randomUUID(), UUID.randomUUID(), account.getCardNumber(), "REST-004", 100.0, 5.0, "PROCESSED", "ok", LocalDateTime.now());
        when(getRewardAccountUseCase.execute(account.getCardNumber())).thenReturn(new RewardAccountDetails(account, List.of(operation)));

        var response = controller.getAccount(account.getCardNumber());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account.getCardNumber(), response.getBody().cardNumber());
        assertEquals(1, response.getBody().operations().size());
    }
}
