package com.jeequan.jeepay.core.config;


import com.jeequan.jeepay.core.entity.SSOUser;
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
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final MfaRepository mfaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SSOUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                mfaRepository.isMfaEnabled(user.getUserId()),
                mfaRepository.getSecret(user.getUserId()),
                user.getFailedAttempts() < 3, // 示例锁定策略
                user.getLockUntil().toInstant(),
                getAuthorities(user.getUserId())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Long userId) {
        // 从sso_user_system表获取角色权限
        return userSystemRepository.findByUserId(userId).stream()
                .map(us -> new SimpleGrantedAuthority("ROLE_" + us.getRole()))
                .collect(Collectors.toList());
    }
}
