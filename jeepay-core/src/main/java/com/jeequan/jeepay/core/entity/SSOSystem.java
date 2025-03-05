package com.jeequan.jeepay.core.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SSOSystem {
    private Long systemId;
    private String systemName;
    private String domainName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Getters and Setters
}