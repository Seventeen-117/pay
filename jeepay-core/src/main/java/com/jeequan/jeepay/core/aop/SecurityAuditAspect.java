package com.jeequan.jeepay.core.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;

// SecurityAuditAspect.java
@Aspect
@Component
public class SecurityAuditAspect {

    @AfterReturning(pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))",
            returning = "authentication")
    public void auditSuccess(Authentication authentication) {
        logAction(authentication.getName(), "LOGIN_SUCCESS");
    }

    @AfterThrowing(pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))",
            throwing = "ex")
    public void auditFailure(AuthenticationException ex) {
        String username = ex.getAuthentication().getName();
        logAction(username, "LOGIN_FAILURE");
    }

    private void logAction(String username, String action) {
        SsoLog log = new SsoLog();
        log.setUserId(userRepository.findByUsername(username).map(SsoUser::getUserId));
        log.setActionType(action);
        log.setDescription("Authentication attempt");
        logRepository.save(log);
    }
}