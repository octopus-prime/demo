package com.example.rechnung.service;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static com.example.kunde.api.KundeApiData.KUNDE_DTO;
import static com.example.kunde.api.KundeApiData.KUNDE_ID;
import static com.example.produkt.api.ProduktApiData.PRODUKT_DTOS;
import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Provider("rechnung-service")
@PactFolder("src/test/resources/pacts")
@AutoConfigureWireMock(port = 9999)
public class PactVerificationTest {

//    @MockBean
//    private KundeApi kundeApi;
//
//    @MockBean
//    private ProduktApi produktApi;
//
//    @MockBean
//    private RechnungRepository rechnungRepository;

    @Autowired
    private RechnungRepository rechnungRepository;

    @BeforeEach
    void setUp(@LocalServerPort final int port, final PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port, "/rechnung-api"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(final PactVerificationContext context) {
        context.verifyInteraction();
    }

    private final MappingBuilder getKunde = get(urlPathEqualTo("/kunde-api/kunden/" + KUNDE_ID));
    private final MappingBuilder getProdukte = get(urlPathEqualTo("/produkt-api/produkte"));

    @BeforeEach
    void setUp() {
//        when(kundeApi.getKunde(KUNDE_ID)).thenReturn(null);
//        when(produktApi.getProdukte(PRODUKT_IDS)).thenReturn(Set.of());
//        when(rechnungRepository.findById(RechnungApiData.RECHNUNG_ID)).thenReturn(Optional.empty());
        givenThat(getKunde.willReturn(notFound()));
        givenThat(getProdukte.willReturn(okForJson(Set.of())));
        rechnungRepository.deleteAll();
    }

    @State("a customer")
    void aCustomer() {
//        when(kundeApi.getKunde(KUNDE_ID)).thenReturn(KUNDE_DTO);
        givenThat(getKunde.willReturn(okForJson(KUNDE_DTO)));
    }

    @State("two products")
    void twoProducts() {
//        when(produktApi.getProdukte(PRODUKT_IDS)).thenReturn(PRODUKT_DTOS);
        givenThat(getProdukte.willReturn(okForJson(PRODUKT_DTOS)));
    }

    @State("a bill")
    void aBill() {
//        when(rechnungRepository.findById(RechnungApiData.RECHNUNG_ID)).thenReturn(Optional.of(RechnungServiceData.RECHNUNG));
        rechnungRepository.save(RechnungServiceData.RECHNUNG);
    }
}
