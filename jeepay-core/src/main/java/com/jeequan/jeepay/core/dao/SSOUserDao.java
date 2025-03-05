package com.jeequan.jeepay.core.dao;


import com.jeequan.jeepay.core.entity.SSOUser;

import java.time.LocalDateTime;

public interface SSOUserDao {
    SSOUser findByUsername(String username);
    void updateUser(SSOUser user);
    void insertLoginAttempt(Long userId, String ipAddress, boolean isSuccess);
    int countFailedAttempts(Long userId, LocalDateTime startTime);
    void lockUser(Long userId, LocalDateTime lockUntil);
    void unlockUser(Long userId);
}