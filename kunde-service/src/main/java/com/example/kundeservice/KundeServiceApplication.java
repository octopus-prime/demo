package com.example.kundeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KundeServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(KundeServiceApplication.class, args);
    }
}
