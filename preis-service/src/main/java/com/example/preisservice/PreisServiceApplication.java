package com.example.preisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PreisServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PreisServiceApplication.class, args);
    }
}
