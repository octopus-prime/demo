package com.example.rechnungservice.impl.produktservice;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.produkt.api.Produkt;
import com.example.rechnungservice.impl.ProduktClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static com.example.rechnungservice.impl.produktservice.ProduktData.*;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
class ProduktClientTest {

    private static final UUID NON_EXISTING_PRODUKT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Autowired
    private ProduktClient client;

    @Nested
    @PactTestFor(providerName = "produkt-service", port = "9999")
    class Ok {

        @Pact(provider = "produkt-service", consumer = "rechnung-service")
        RequestResponsePact pact(final PactDslWithProvider builder) {
            return builder
                    .given("default")
                    .uponReceiving("Get existing produkt")
                    .method("GET")
                    .path("/produkte")
                    .matchQuery("produktIds", ".*", Arrays.asList(PRODUKT1_ID.toString(), PRODUKT2_ID.toString()))
                    .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .willRespondWith()
                    .status(HttpStatus.OK.value())
//                    .matchHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .body(createProdukte())
                    .toPact();
        }

        private DslPart createProdukte() {
            return new PactDslJsonArray()
                    .template(createProdukt(PRODUKT1))
                    .template(createProdukt(PRODUKT2));
        }

        private DslPart createProdukt(final Produkt produkt) {
            return new PactDslJsonBody()
                    .uuid("id", produkt.getId())
                    .stringType("bezeichnung", produkt.getBezeichnung())
                    .stringType("beschreibung", produkt.getBeschreibung());
        }

        @Test
        @DisplayName("Should give produkt")
        void test() {
            final Set<Produkt> produkte = client.getProdukte(Set.of(PRODUKT1_ID, PRODUKT2_ID));
            then(produkte).contains(PRODUKT1, PRODUKT2);
        }
    }

    @Nested
    @PactTestFor(providerName = "produkt-service", port = "9999")
    class NotFound {

        @Pact(provider = "produkt-service", consumer = "rechnung-service")
        RequestResponsePact pact(final PactDslWithProvider builder) {
            return builder
                    .given("default")
                    .uponReceiving("Get non-existing produkt")
                    .method("GET")
                    .path("/produkte")
                    .matchQuery("produktIds", ".*", Collections.singletonList(NON_EXISTING_PRODUKT_ID.toString()))
                    .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .willRespondWith()
                    .status(HttpStatus.OK.value())
                    .toPact();
        }

        @Test
        @DisplayName("Should give empty")
        void test() {
            final Set<Produkt> produkte = client.getProdukte(Set.of(NON_EXISTING_PRODUKT_ID));
            then(produkte).isNullOrEmpty();
        }
    }
}
