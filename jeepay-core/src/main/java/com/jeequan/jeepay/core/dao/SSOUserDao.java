package com.jeequan.jeepay.core.dao;


import com.jeequan.jeepay.core.entity.SsoUser;

import java.time.LocalDateTime;

public interface SSOUserDao {
    SsoUser findByUsername(String username);
    void updateUser(SsoUser user);
    void insertLoginAttempt(Long userId, String ipAddress, boolean isSuccess);
    int countFailedAttempts(Long userId, LocalDateTime startTime);
    void lockUser(Long userId, LocalDateTime lockUntil);
    void unlockUser(Long userId);
}