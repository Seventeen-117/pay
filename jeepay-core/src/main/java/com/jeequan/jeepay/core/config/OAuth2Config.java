package com.jeequan.jeepay.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class OAuth2Config {
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        JdbcRegisteredClientRepository registeredClientRepository =
                new JdbcRegisteredClientRepository(jdbcTemplate);

        // 初始化示例客户端（仅开发环境需要）
        if (registeredClientRepository.findByClientId("web-app") == null) {
            RegisteredClient webApp = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("web-app")
                    .clientSecret("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("http://localhost:8080/login/oauth2/code/web-app")
                    .scope("user.read")
                    .scope("user.write")
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(true)
                            .build())
                    .build();

            registeredClientRepository.save(webApp);
        }

        return registeredClientRepository;
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository());
    }

    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator() {
        return new OAuth2AccessTokenGenerator();
    }
}