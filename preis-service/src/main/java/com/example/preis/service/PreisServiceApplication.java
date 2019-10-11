package com.example.preis.service;

import com.example.common.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootApplication
@EnableMongoRepositories
@Import(LoggingConfiguration.class)
public class PreisServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PreisServiceApplication.class, args);
    }

    @Component
    public class CommandLineAppStartupRunner implements CommandLineRunner {

        @Autowired
        private PreisRepository preisRepository;

        @Override
        @Transactional
        public void run(final String... args) {
            final Preis preis1 = Preis.builder()
                    .id(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
                    .produktId(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
                    .amount(2313.39)
                    .currency("€")
                    .build();
            final Preis preis2 = Preis.builder()
                    .id(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
                    .produktId(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
                    .amount(1199.99)
                    .currency("€")
                    .build();
            if (!preisRepository.existsById(preis1.getId()))
                preisRepository.save(preis1);
            if (!preisRepository.existsById(preis2.getId()))
                preisRepository.save(preis2);
        }
    }
}
