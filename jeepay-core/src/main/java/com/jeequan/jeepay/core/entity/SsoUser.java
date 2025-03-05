package com.jeequan.jeepay.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sso_user")
@Getter
@Setter
public class SsoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;
    private String password;
    private boolean mfaEnabled;
    private String mfaSecret;
    private boolean isLocked;
    private LocalDateTime lockUntil;
    private int failedAttempts;
    // 其他字段...
}