package com.example.rechnung.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RechnungServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RechnungServiceApplication.class, args);
    }
}
