package com.jeequan.jeepay.core.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;// MfaAuthenticationFilter.java

import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class MfaAuthenticationFilter extends OncePerRequestFilter {
    private final MfaService mfaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() &&
                auth.getPrincipal() instanceof CustomUserDetails user) {
            if (user.isMfaEnabled() && !request.getRequestURI().contains("/verify-mfa")) {
                String code = mfaService.generateCode(user.getMfaSecret());
                mfaService.sendVerificationCode(user.getUsername(), code);
                response.sendRedirect("/mfa-verify");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}


