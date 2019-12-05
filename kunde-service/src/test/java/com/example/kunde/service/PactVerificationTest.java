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
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Provider("kunde-service")
@PactFolder("src/test/resources/pacts")
public class PactVerificationTest {

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private AdresseRepository adresseRepository;

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
        kundeRepository.deleteAll();
        adresseRepository.deleteAll();
    }

    @State("a customer")
    void aCustomer() {
        final Adresse rechnungsadresse = Adresse.builder()
                .id(UUID.randomUUID())
                .strasse("Musterstrasse")
                .hausnummer("17a")
                .plz("12345")
                .wohnort("Musterstadt")
                .build();
        adresseRepository.save(rechnungsadresse);

        final Adresse lieferadresse = Adresse.builder()
                .id(UUID.randomUUID())
                .strasse("Musterstrasse")
                .hausnummer("17b")
                .plz("12345")
                .wohnort("Musterstadt")
                .build();
        adresseRepository.save(lieferadresse);

        final Kunde kunde = Kunde.builder()
                .id(UUID.fromString("bf73ce21-f91b-4619-8891-1b4b471db3fd"))
                .vorname("Max")
                .nachname("Mustermann")
                .rechnungsadresse(rechnungsadresse)
                .lieferadresse(lieferadresse)
                .build();
        kundeRepository.save(kunde);
    }

    @State("without address")
    void withoutAddress() {
        kundeRepository.findById(UUID.fromString("bf73ce21-f91b-4619-8891-1b4b471db3fd"))
                .ifPresent(kunde -> kunde.setLieferadresse(null));
    }
}
