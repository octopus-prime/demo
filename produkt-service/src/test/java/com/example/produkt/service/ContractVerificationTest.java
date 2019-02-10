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

import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Provider("produkt-service")
@PactFolder("../rechnung-service/target/pacts")
class ContractVerificationTest {

    @Autowired
    private ProduktRepository produktRepository;

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
        final Produkt produkt1 = Produkt.builder()
                .id(UUID.fromString("2d9f379e-4b5f-4439-ad67-ac9e9cc722d3"))
                .bezeichnung("foo")
                .beschreibung("bar")
                .build();
        final Produkt produkt2 = Produkt.builder()
                .id(UUID.fromString("5115600f-30dd-4f67-92d8-4ecbe7e11d0f"))
                .bezeichnung("foo")
                .beschreibung("bar")
                .build();
        produktRepository.save(produkt1);
        produktRepository.save(produkt2);
    }
}
