package com.example.kunde.service;

import com.example.common.LoggingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
@Import(LoggingConfiguration.class)
public class KundeServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(KundeServiceApplication.class, args);
    }

    @Component
    public class CommandLineAppStartupRunner implements CommandLineRunner {

        @Autowired
        private KundeRepository kundeRepository;

        @Autowired
        private AdresseRepository adresseRepository;

        @Override
        public void run(final String... args) {
            final var adresse = Adresse.builder()
                    .id(UUID.randomUUID())
                    .strasse("Musterstrasse")
                    .hausnummer("17a")
                    .plz("12345")
                    .wohnort("Musterstadt")
                    .build();
            final var kunde = Kunde.builder()
                    .id(UUID.fromString("bf73ce21-f91b-4619-8891-1b4b471db3fd"))
                    .vorname("Max")
                    .nachname("Mustermann")
                    .rechnungsadresse(adresse)
                    .lieferadresse(adresse)
                    .build();
            kundeRepository.deleteAll();
            adresseRepository.save(adresse);
            kundeRepository.save(kunde);
        }
    }
}
