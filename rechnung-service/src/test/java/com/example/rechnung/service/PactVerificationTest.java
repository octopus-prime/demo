package com.example.rechnung.service;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.example.kunde.api.AdresseDto;
import com.example.kunde.api.KundeDto;
import com.example.produkt.api.ProduktDto;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

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
        context.setTarget(new HttpTestTarget("localhost", port, "/rechnung-api"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(final PactVerificationContext context) {
        context.verifyInteraction();
    }

    private final MappingBuilder getKunde = get(urlPathEqualTo("/kunde-api/kunden/bf73ce21-f91b-4619-8891-1b4b471db3fd"));
    private final MappingBuilder getProdukte = get(urlPathEqualTo("/produkt-api/produkte"));

    @BeforeEach
    void setUp() {
        givenThat(getKunde.willReturn(notFound()));
        givenThat(getProdukte.willReturn(okForJson(Set.of())));
        rechnungRepository.deleteAll();
    }

    @State("a customer")
    void aCustomer() {
        final AdresseDto rechnungsadresse = AdresseDto.builder()
                .strasse("Musterstrasse")
                .hausnummer("17a")
                .plz("12345")
                .wohnort("Musterstadt")
                .build();
        final AdresseDto lieferadresse = AdresseDto.builder()
                .strasse("Musterstrasse")
                .hausnummer("17b")
                .plz("12345")
                .wohnort("Musterstadt")
                .build();
        final KundeDto kunde = KundeDto.builder()
                .id(UUID.fromString("bf73ce21-f91b-4619-8891-1b4b471db3fd"))
                .vorname("Max")
                .nachname("Mustermann")
                .rechnungsadresse(rechnungsadresse)
                .lieferadresse(lieferadresse)
                .build();
        givenThat(getKunde.willReturn(okForJson(kunde)));
    }

    @State("two products")
    void twoProducts() {
        final ProduktDto produkt1 = ProduktDto.builder()
                .id(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
                .bezeichnung("Asus ROG Swift PG27UQ")
                .beschreibung("68,58 cm (27 Zoll) Gaming Monitor (4K UHD, bis zu 144Hz, G-Sync, HDR, Quantom-Dot, Aura Sync, DisplayPort, HDMI) schwarz")
                .preis(BigDecimal.valueOf(2313.39))
                .build();
        final ProduktDto produkt2 = ProduktDto.builder()
                .id(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
                .bezeichnung("Palit GeForce RTX 2080 Ti GamingPro OC, Grafikkarte")
                .beschreibung("High-End-Grafikkarte mit der GeForce RTX 2080 Ti GPU von NVIDIA")
                .preis(BigDecimal.valueOf(1199.99))
                .build();
        givenThat(getProdukte.willReturn(okForJson(Set.of(produkt1, produkt2))));
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
