package com.example.kundeservice;

import com.example.common.LoggingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(LoggingConfiguration.class)
public class KundeServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(KundeServiceApplication.class, args);
    }
}
