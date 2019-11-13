package com.example.common.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class MetricsConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        final var service = Tag.of("service", applicationName);
        final var api = Tag.of("api", contextPath);
        final var tags = Set.of(service, api);
        return registry -> registry.config().commonTags(tags);
    }
}
