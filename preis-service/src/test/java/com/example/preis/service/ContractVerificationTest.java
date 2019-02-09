package com.example.preis.service;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.example.preis.api.Preis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Provider("preis-service")
@PactFolder("../rechnung-service/target/pacts")
class ContractVerificationTest {

    @Autowired
    private PreisRepository preisRepository;

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
        preisRepository.save(preis1);
        preisRepository.save(preis2);
    }
}
