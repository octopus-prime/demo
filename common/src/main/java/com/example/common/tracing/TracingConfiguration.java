package com.example.common.tracing;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@ConditionalOnMissingBean(value = TraceableExecutorService.class)
public class TracingConfiguration {

    @Bean
    public TraceableExecutorService traceableExecutorService(final BeanFactory beanFactory) {
        return new TraceableExecutorService(beanFactory, Executors.newCachedThreadPool());
    }
}
