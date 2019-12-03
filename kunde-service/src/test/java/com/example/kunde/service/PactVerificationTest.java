package com.example.kunde.service;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.example.kunde.api.KundeApiData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

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
        context.setTarget(new HttpTestTarget("localhost", port, "/kunde-api"));
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
        adresseRepository.save(KundeServiceData.ADRESSE);
        kundeRepository.save(KundeServiceData.KUNDE);
    }

    @State("without address")
    void withoutAddress() {
        kundeRepository.findById(KundeApiData.KUNDE_ID)
                .ifPresent(kunde -> kunde.setLieferadresse(null));
    }
}
