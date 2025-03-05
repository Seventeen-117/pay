package com.jeequan.jeepay.core.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;// MfaAuthenticationFilter.java

import javax.servlet.ServletException;
import java.io.IOException;

public class MfaAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() &&
                auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            if (userDetails.isMfaEnabled() && !isMfaVerified(request)) {
                redirectToMfaVerification(response, userDetails);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void redirectToMfaVerification(HttpServletResponse response, CustomUserDetails userDetails) {
        // 生成并存储验证码
        String verificationCode = generateVerificationCode();
        cacheService.storeMfaCode(userDetails.getUsername(), verificationCode);

        // 重定向到MFA验证页面
        response.sendRedirect("/mfa-verify?code=" + verificationCode);
    }
}


