package com.jeequan.jeepay.core.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MfaService {
    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
    private final CacheService cacheService;

    public String generateSecretKey() {
        return googleAuthenticator.createCredentials().getKey();
    }

    public String generateQrCode(String email, String secret) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(
                "YourApp",
                email,
                new GoogleAuthenticatorKey.Builder(secret).build()
        );
    }

    public boolean validateCode(String secret, String code) {
        try {
            return googleAuthenticator.authorize(secret, Integer.parseInt(code));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void sendVerificationCode(String username, String code) {
        cacheService.storeMfaCode(username, code);
        // 实际应调用短信/邮件服务
    }
}