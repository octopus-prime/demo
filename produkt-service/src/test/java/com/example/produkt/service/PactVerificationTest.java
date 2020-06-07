package com.example.produkt.service;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Provider("produkt-service")
@PactFolder("src/test/resources/pacts")
class PactVerificationTest {

    @Autowired
    private ProduktRepository produktRepository;

    @BeforeEach
    void setUp(@LocalServerPort final int port, final PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(final PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void setUp() {
        produktRepository.deleteAll();
    }

    @State("two products")
    void twoProducts() {
        final Produkt produkt1 = Produkt.builder()
                .id(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
                .bezeichnung("Asus ROG Swift PG27UQ")
                .beschreibung("68,58 cm (27 Zoll) Gaming Monitor (4K UHD, bis zu 144Hz, G-Sync, HDR, Quantom-Dot, Aura Sync, DisplayPort, HDMI) schwarz")
                .preis(BigDecimal.valueOf(2313.39))
                .build();
        produktRepository.save(produkt1);

        final Produkt produkt2 = Produkt.builder()
                .id(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
                .bezeichnung("Palit GeForce RTX 2080 Ti GamingPro OC, Grafikkarte")
                .beschreibung("High-End-Grafikkarte mit der GeForce RTX 2080 Ti GPU von NVIDIA")
                .preis(BigDecimal.valueOf(1199.99))
                .build();
        produktRepository.save(produkt2);
    }
}
