package com.jeequan.jeepay.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SsoUserRepository userRepository;
    private final SsoUserSystemRepository userSystemRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 实现内容保持不变
    }
}