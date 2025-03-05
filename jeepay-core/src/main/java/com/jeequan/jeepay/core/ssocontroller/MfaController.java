package com.jeequan.jeepay.core.ssocontroller;

import com.jeequan.jeepay.core.config.CustomUserDetails;
import com.jeequan.jeepay.core.config.MfaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MfaController {
    private final MfaService mfaService;

    @PostMapping("/verify-mfa")
    public ResponseEntity<?> verifyCode(@RequestParam String code,
                                        @AuthenticationPrincipal CustomUserDetails user) {
        if (mfaService.validateCode(user.getMfaSecret(), code)) {
            return ResponseEntity.ok().build();
        }
        throw new BadCredentialsException("Invalid verification code");
    }
}
