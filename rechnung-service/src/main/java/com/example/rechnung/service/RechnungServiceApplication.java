package com.example.rechnung.service;

import feign.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableFeignClients
public class RechnungServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RechnungServiceApplication.class, args);
    }

    @Bean
    TraceableExecutorService traceableExecutorService(final BeanFactory beanFactory) {
        return new TraceableExecutorService(beanFactory, Executors.newCachedThreadPool());
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
