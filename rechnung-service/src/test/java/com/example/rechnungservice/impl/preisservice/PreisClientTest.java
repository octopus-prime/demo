package com.example.rechnungservice.impl.preisservice;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.rechnungservice.impl.preisservice.api.Preis;
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
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.example.rechnungservice.impl.preisservice.PreisData.PREIS1;
import static com.example.rechnungservice.impl.preisservice.PreisData.PREIS2;
import static com.example.rechnungservice.impl.produktservice.ProduktData.PRODUKT1_ID;
import static com.example.rechnungservice.impl.produktservice.ProduktData.PRODUKT2_ID;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
class PreisClientTest {

    @Autowired
    private PreisClient client;

    @Nested
    @PactTestFor(providerName = "preis-service", port = "9999")
    class Ok {

        @Pact(provider = "preis-service", consumer = "rechnung-service")
        RequestResponsePact pact(final PactDslWithProvider builder) {
            return builder
                    .given("default")
                    .uponReceiving("Get existing preis")
                    .method("GET")
                    .path("/preise")
                    .matchQuery("produktIds", ".*", Arrays.asList(PRODUKT1_ID.toString(), PRODUKT2_ID.toString()))
                    .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .willRespondWith()
                    .status(HttpStatus.OK.value())
                    .matchHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .body(createPreise())
                    .toPact();
        }

        private DslPart createPreise() {
            return new PactDslJsonBody()
                    .object(PRODUKT1_ID.toString(), createPreis(PREIS1))
                    .object(PRODUKT2_ID.toString(), createPreis(PREIS2));
        }

        private DslPart createPreis(final Preis preis) {
            return new PactDslJsonBody()
                    .numberType("amount", preis.getAmount())
                    .stringType("currency", preis.getCurrency());
        }

        @Test
        @DisplayName("Should give preis")
        void test() {
            final Map<UUID, Preis> preise = client.getPreise(Set.of(PRODUKT1_ID, PRODUKT2_ID));
            then(preise).containsKeys(PRODUKT1_ID, PRODUKT2_ID).containsValues(PREIS1, PREIS2);
        }
    }

    @Nested
    @PactTestFor(providerName = "preis-service", port = "9999")
    class NotFound {

        @Pact(provider = "preis-service", consumer = "rechnung-service")
        RequestResponsePact pact(final PactDslWithProvider builder) {
            return builder
                    .given("default")
                    .uponReceiving("Get non-existing preis")
                    .method("GET")
                    .path("/preise")
                    .matchQuery("produktIds", ".*", Arrays.asList(PRODUKT1_ID.toString(), PRODUKT2_ID.toString()))
                    .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .willRespondWith()
                    .status(HttpStatus.NOT_FOUND.value())
                    .toPact();
        }

        @Test
        @DisplayName("Should give empty")
        void test() {
            final Map<UUID, Preis> preise = client.getPreise(Set.of(PRODUKT1_ID, PRODUKT2_ID));
            then(preise).isNullOrEmpty();
        }
    }
}
