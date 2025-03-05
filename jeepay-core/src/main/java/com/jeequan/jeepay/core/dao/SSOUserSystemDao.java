package com.jeequan.jeepay.core.dao;

import com.jeequan.jeepay.core.entity.SSOUserSystem;

import java.util.List;

public interface SSOUserSystemDao {
    List<SSOUserSystem> findByUserId(Long userId);
}