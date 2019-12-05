package com.example.rechnung.service;

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
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Provider("rechnung-service")
@PactFolder("src/test/resources/pacts")
@AutoConfigureWireMock(port = 9999)
public class PactVerificationTest {

    @Autowired
    private RechnungRepository rechnungRepository;

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
        rechnungRepository.deleteAll();
    }

    @State("a bill")
    void aBill() {
        final Rechnung rechnung = Rechnung.builder()
                .rechnungId(UUID.fromString("2d9f379e-4b5f-4439-ad67-ac9e9cc722d3"))
                .vorname("Max")
                .nachname("Mustermann")
                .strasse("Musterstrasse")
                .hausnummer("17a")
                .plz("12345")
                .wohnort("Musterstadt")
                .warenkorb(List.of(
                        Rechnung.Posten.builder()
                                .anzahl(2)
                                .produkt("Asus ROG Swift PG27UQ")
                                .preis(BigDecimal.valueOf(2313.39))
                                .build(),
                        Rechnung.Posten.builder()
                                .anzahl(1)
                                .produkt("Palit GeForce RTX 2080 Ti GamingPro OC, Grafikkarte")
                                .preis(BigDecimal.valueOf(1199.99))
                                .build()
                ))
                .build();
        rechnungRepository.save(rechnung);
    }
}
