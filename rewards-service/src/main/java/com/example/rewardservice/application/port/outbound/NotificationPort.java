package com.example.rewardservice.application.port.outbound;

import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;

public interface NotificationPort {
    void sendRewardNotification(RewardAccount account, RewardOperation operation);
}