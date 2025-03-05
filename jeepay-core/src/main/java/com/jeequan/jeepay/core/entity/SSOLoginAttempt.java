package com.jeequan.jeepay.core.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SSOLoginAttempt {
    private Long attemptId;
    private Long userId;
    private String ipAddress;
    private boolean isSuccess;
    private LocalDateTime attemptTime;
    // Getters and Setters
}