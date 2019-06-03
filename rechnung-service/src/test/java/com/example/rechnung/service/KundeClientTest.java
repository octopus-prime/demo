package com.example.rechnung.service;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.kunde.api.KundeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static com.example.rechnung.service.KundeData.KUNDE_DTO;
import static com.example.rechnung.service.KundeData.KUNDE_ID;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(properties = "kunde-service.ribbon.listOfServers=localhost:9999")
@ExtendWith(PactConsumerTestExt.class)
class KundeClientTest {

    private static final UUID NON_EXISTING_KUNDE_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Autowired
    private KundeClient client;

    @Nested
    @PactTestFor(providerName = "kunde-service", port = "9999")
    class Ok {

        @Pact(provider = "kunde-service", consumer = "rechnung-service")
        RequestResponsePact pact(final PactDslWithProvider builder) {
            return builder
                    .given("default")
                    .uponReceiving("Get existing kunde")

                    .method("GET")
                    .path("/kunden/" + KUNDE_ID)
                    .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .willRespondWith()

                    .status(HttpStatus.OK.value())
                    .body(createKunde())
                    .toPact();
        }

        private DslPart createKunde() {
            return new PactDslJsonBody()
                    .uuid("id", KUNDE_DTO.getId())
                    .stringType("vorname", KUNDE_DTO.getVorname())
                    .stringType("nachname", KUNDE_DTO.getNachname())
                    .object("rechnungsadresse", createAdresse(KUNDE_DTO.getRechnungsadresse()))
                    .object("lieferadresse", createAdresse(KUNDE_DTO.getLieferadresse()));
        }

        private DslPart createAdresse(final KundeDto.Adresse adresse) {
            return new PactDslJsonBody()
                    .stringType("strasse", adresse.getStrasse())
                    .stringType("hausnummer", adresse.getHausnummer())
                    .stringType("plz", adresse.getPlz())
                    .stringType("wohnort", adresse.getWohnort());
        }

        @Test
        @DisplayName("Should give kunde")
        void test() {
            then(client.getKunde(KUNDE_ID)).isEqualTo(KUNDE_DTO);
        }
    }

    @Nested
    @PactTestFor(providerName = "kunde-service", port = "9999")
    class NotFound {

        @Pact(provider = "kunde-service", consumer = "rechnung-service")
        RequestResponsePact pact(final PactDslWithProvider builder) {
            return builder
                    .given("default")
                    .uponReceiving("Get non-existing kunde")

                    .method("GET")
                    .path("/kunden/" + NON_EXISTING_KUNDE_ID)
                    .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .willRespondWith()

                    .status(HttpStatus.NOT_FOUND.value())
                    .toPact();
        }

        @Test
        @DisplayName("Should give empty")
        void test() {
            then(client.getKunde(NON_EXISTING_KUNDE_ID)).isNull();
        }
    }
}
