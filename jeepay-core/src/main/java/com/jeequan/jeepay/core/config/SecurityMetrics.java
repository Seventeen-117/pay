package com.jeequan.jeepay.core.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityMetrics {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
                .commonTags("application", "sso-server");
    }

    @Bean
    public Counter loginAttemptsCounter(MeterRegistry registry) {
        return Counter.builder("sso.login.attempts")
                .description("Total login attempts")
                .register(registry);
    }

}