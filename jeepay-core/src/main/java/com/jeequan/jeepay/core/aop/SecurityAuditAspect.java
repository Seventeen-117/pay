package com.jeequan.jeepay.core.aop;

import com.jeequan.jeepay.core.config.CustomUserDetails;
import com.jeequan.jeepay.core.entity.SsoUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;

// SecurityAuditAspect.java
@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAuditAspect {
    private final SsoLogRepository logRepository;
    private final HttpServletRequest request;

    @AfterReturning(
            pointcut = "execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))",
            returning = "authentication"
    )
    public void auditSuccess(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUserDetails user) {
            logRepository.save(new SsoLog(
                    user.getUserId(),
                    "LOGIN_SUCCESS",
                    "Successful authentication",
                    request.getRemoteAddr()
            ));
        }
    }

    @AfterThrowing(
            pointcut = "execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))",
            throwing = "ex"
    )
    public void auditFailure(AuthenticationException ex) {
        String username = ex.getAuthentication().getName();
        userRepository.findByUsername(username).ifPresent(user ->
                logRepository.save(new SsoLog(
                        user.getUserId(),
                        "LOGIN_FAILURE",
                        "Authentication failed: " + ex.getMessage(),
                        request.getRemoteAddr()
                ))
        );
    }
}
