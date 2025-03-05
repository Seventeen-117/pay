package com.jeequan.jeepay.core.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SSOUser {
    private Long userId;
    private String username;
    private String password;
    private boolean isLocked;
    private LocalDateTime lockUntil;
    private int failedAttempts;
    // Getters and Setters
}
