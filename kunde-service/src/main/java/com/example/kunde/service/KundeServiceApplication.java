package com.example.kunde.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KundeServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(KundeServiceApplication.class);
    }
}
