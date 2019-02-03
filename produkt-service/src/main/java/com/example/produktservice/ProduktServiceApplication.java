package com.example.produktservice;

import com.example.produktservice.api.Produkt;
import com.example.produktservice.impl.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
//@Import(SpringDataRestConfiguration.class)
@EnableCaching
public class ProduktServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ProduktServiceApplication.class, args);
    }

    @Component
    public class CommandLineAppStartupRunner implements CommandLineRunner {

        @Autowired
        private ProduktRepository produktRepository;

        @Override
        public void run(final String... args) {
            final Produkt produkt1 = Produkt.builder()
                    .id(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
                    .bezeichnung("Asus ROG Swift PG27UQ")
                    .beschreibung("68,58 cm (27 Zoll) Gaming Monitor (4K UHD, bis zu 144Hz, G-Sync, HDR, Quantom-Dot, Aura Sync, DisplayPort, HDMI) schwarz")
                    .build();
            final Produkt produkt2 = Produkt.builder()
                    .id(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
                    .bezeichnung("Palit GeForce RTX 2080 Ti GamingPro OC, Grafikkarte")
                    .beschreibung("High-End-Grafikkarte mit der GeForce RTX 2080 Ti GPU von NVIDIA")
                    .build();
            produktRepository.deleteAll();
            produktRepository.save(produkt1);
            produktRepository.save(produkt2);
        }
    }
}
