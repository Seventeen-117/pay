package com.jeequan.jeepay.core.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Data
// CustomUserDetails.java
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private boolean mfaEnabled;
    private String mfaSecret;
    private boolean accountNonLocked;
    private Instant lockUntil;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    // 实现UserDetails接口方法
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked && (lockUntil == null || lockUntil.isBefore(Instant.now()));
    }

    // 其他getter/setter
}
