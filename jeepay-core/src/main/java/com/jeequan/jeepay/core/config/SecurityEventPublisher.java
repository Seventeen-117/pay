package com.jeequan.jeepay.core.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SecurityEventPublisher {
    @Bean
    public ApplicationEventPublisher securityEventPublisher(SimpMessagingTemplate messagingTemplate) {
        return event -> {
            if (event instanceof AbstractAuthenticationEvent authEvent) {
                Map<String, Object> data = new HashMap<>();
                data.put("timestamp", Instant.now());
                data.put("principal", authEvent.getAuthentication().getName());
                data.put("type", authEvent.getClass().getSimpleName());

                messagingTemplate.convertAndSend("/topic/security-events", data);
            }
        };
    }
}
