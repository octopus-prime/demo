package com.example.rechnungservice;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicatorRegistry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

import static java.util.Optional.ofNullable;
import static org.springframework.boot.actuate.health.Health.Builder;

//@Configuration
class HealthConfiguration {

    private final ApplicationContext applicationContext;
    private final HealthIndicatorRegistry healthIndicatorRegistry;
    private final EurekaClient discoveryClient;

    @Autowired
    HealthConfiguration(final ApplicationContext applicationContext,
                        final HealthIndicatorRegistry healthIndicatorRegistry,
                        final EurekaClient discoveryClient) {
        this.applicationContext = applicationContext;
        this.healthIndicatorRegistry = healthIndicatorRegistry;
        this.discoveryClient = discoveryClient;
    }

    @PostConstruct
    void registerHealthClients() {
        applicationContext.getBeansWithAnnotation(FeignClient.class).forEach(this::registerHealthClient);
    }

    private void registerHealthClient(final String key, final Object unused) {
        ofNullable(applicationContext.findAnnotationOnBean(key, FeignClient.class)).ifPresent(this::registerHealthClient);
    }

    private void registerHealthClient(final FeignClient client) {
        final HealthIndicator indicator = () -> {
            final var builder = new Builder();
            if (isUp(client.name())) {
                builder.up();
            } else {
                builder.down();
            }
            return builder.build();
        };
        healthIndicatorRegistry.register(client.name(), indicator);
    }

    private boolean isUp(final String service) {
        return discoveryClient.getInstancesByVipAddress(service, false).stream()
                .anyMatch(info -> info.getStatus() == InstanceInfo.InstanceStatus.UP);
    }
}
