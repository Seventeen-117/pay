package com.jeequan.jeepay.core.dao;


import com.jeequan.jeepay.core.entity.SSOToken;

public interface SSOTokenDao {
    void insertToken(SSOToken token);
    SSOToken findByTokenId(String tokenId);
    void deleteToken(String tokenId);
}