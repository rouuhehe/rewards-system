package com.example.rewardservice.application.dto;

import com.example.rewardservice.domain.model.RewardAccount;
import com.example.rewardservice.domain.model.RewardOperation;

import java.util.List;

public record RewardAccountDetails(RewardAccount account, List<RewardOperation> operations) {
}