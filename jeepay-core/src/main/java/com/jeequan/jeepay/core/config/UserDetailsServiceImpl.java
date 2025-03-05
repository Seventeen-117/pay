package com.jeequan.jeepay.core.config;


import com.jeequan.jeepay.core.entity.SsoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

// UserDetailsServiceImpl.java
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SsoUserRepository userRepository;
    private final SsoUserSystemRepository userSystemRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SsoUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.isMfaEnabled(),
                user.getMfaSecret(),
                user.getFailedAttempts() < 3,
                user.getLockUntil().toInstant(),
                getAuthorities(user.getUserId())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Long userId) {
        return userSystemRepository.findByUserId(userId).stream()
                .map(us -> new SimpleGrantedAuthority("ROLE_" + us.getRole()))
                .collect(Collectors.toList());
    }
}

