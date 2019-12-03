package com.example.produkt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@EnableMongoRepositories
public class ProduktServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ProduktServiceApplication.class);
    }

    @Component
    public class CommandLineAppStartupRunner implements CommandLineRunner {

        @Autowired
        private ProduktRepository produktRepository;

        @Override
        @Transactional
        public void run(final String... args) {
            final Produkt produkt1 = Produkt.builder()
                    .id(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
                    .bezeichnung("Asus ROG Swift PG27UQ")
                    .beschreibung("68,58 cm (27 Zoll) Gaming Monitor (4K UHD, bis zu 144Hz, G-Sync, HDR, Quantom-Dot, Aura Sync, DisplayPort, HDMI) schwarz")
                    .preis(BigDecimal.valueOf(2313.39))
                    .build();
            final Produkt produkt2 = Produkt.builder()
                    .id(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
                    .bezeichnung("Palit GeForce RTX 2080 Ti GamingPro OC, Grafikkarte")
                    .beschreibung("High-End-Grafikkarte mit der GeForce RTX 2080 Ti GPU von NVIDIA")
                    .preis(BigDecimal.valueOf(1199.99))
                    .build();
            produktRepository.deleteAll();
            if (!produktRepository.existsById(produkt1.getId()))
                produktRepository.save(produkt1);
            if (!produktRepository.existsById(produkt2.getId()))
                produktRepository.save(produkt2);
        }
    }
}
