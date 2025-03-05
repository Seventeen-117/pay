package com.jeequan.jeepay.core.config;

import org.apache.ibatis.ognl.internal.Cache;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheService {
    private final Cache<String, String> mfaCache =
            Caffeine.newBuilder()
                    .expireAfterWrite(5, TimeUnit.MINUTES)
                    .build();

    public void storeMfaCode(String username, String code) {
        mfaCache.put(username, code);
    }

    public String getMfaCode(String username) {
        return mfaCache.getIfPresent(username);
    }
}