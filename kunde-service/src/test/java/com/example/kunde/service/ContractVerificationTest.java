package com.example.kunde.service;

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

import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Provider("kunde-service")
@PactFolder("../rechnung-service/target/pacts")
class ContractVerificationTest {

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    @BeforeEach
    void setUp(@LocalServerPort final int port, final PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(final PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("default")
    void defaultState() {
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
