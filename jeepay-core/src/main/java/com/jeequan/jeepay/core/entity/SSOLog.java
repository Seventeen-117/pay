package com.jeequan.jeepay.core.entity;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class SSOLog {
    private Long logId;
    private Long userId;
    private String actionType;
    private String description;
    private LocalDateTime logTime;
    // Getters and Setters
}