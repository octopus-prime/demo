package com.example.kunde.service;

import com.example.common.LoggingConfiguration;
import com.example.common.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@Import({SwaggerConfiguration.class, LoggingConfiguration.class})
public class KundeServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(KundeServiceApplication.class, args);
    }
}
