package com.jeequan.jeepay.core.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SSOToken {
    private String tokenId;
    private Long userId;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    // Getters and Setters
}