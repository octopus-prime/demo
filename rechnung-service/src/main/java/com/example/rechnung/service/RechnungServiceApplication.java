package com.example.rechnung.service;

import com.example.common.LoggingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@Import(LoggingConfiguration.class)
public class RechnungServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RechnungServiceApplication.class, args);
    }
}
