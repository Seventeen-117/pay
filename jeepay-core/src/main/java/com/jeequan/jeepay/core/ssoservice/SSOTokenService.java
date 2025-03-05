package com.jeequan.jeepay.core.ssoservice;


import com.jeequan.jeepay.core.dao.SSOTokenDao;
import com.jeequan.jeepay.core.entity.SSOToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SSOTokenService {

    @Autowired
    private SSOTokenDao tokenDao;

    public String generateToken(Long userId) {
        String tokenId = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);  // 令牌有效期 30 分钟
        SSOToken token = new SSOToken();
        token.setTokenId(tokenId);
        token.setUserId(userId);
        token.setExpiresAt(expiresAt);
        tokenDao.insertToken(token);
        return tokenId;
    }

    public boolean validateToken(String tokenId) {
        SSOToken token = tokenDao.findByTokenId(tokenId);
        if (token == null) {
            return false;
        }
        return token.getExpiresAt().isAfter(LocalDateTime.now());
    }

    public void invalidateToken(String tokenId) {
        tokenDao.deleteToken(tokenId);
    }
}