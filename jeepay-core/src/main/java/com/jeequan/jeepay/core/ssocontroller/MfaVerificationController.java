package com.jeequan.jeepay.core.ssocontroller;

import com.jeequan.jeepay.core.config.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class MfaVerificationController {
    @PostMapping("/verify-mfa")
    public ResponseEntity<?> verifyMfaCode(@RequestParam String code,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        String storedCode = cacheService.getMfaCode(user.getUsername());
        if (code.equals(storedCode)) {
            // 标记MFA验证完成
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
            return ResponseEntity.ok().build();
        }
        throw new BadCredentialsException("Invalid MFA code");
    }
}
