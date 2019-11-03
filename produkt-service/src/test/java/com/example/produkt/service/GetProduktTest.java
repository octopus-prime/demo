package com.example.produkt.service;

import com.example.common.RestAssuredExtension;
import com.example.produkt.api.ProduktApiData;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(RestAssuredExtension.class)
class GetProduktTest {

    @Autowired
    private ProduktRepository produktRepository;

    @BeforeEach
    void setUp() {
        produktRepository.deleteAll();
    }

    @Test
    @DisplayName("Should give empty")
    void getProduktNotFound() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("produktIds", ProduktApiData.PRODUKT_IDS)

                .when()
                .get("produkte")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonEquals(Set.of()));
    }

    @Test
    @DisplayName("Should give 'ok' and produkte")
    void getProduktOk() {
        produktRepository.save(ProduktServiceData.PRODUKT1);
        produktRepository.save(ProduktServiceData.PRODUKT2);

        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("produktIds", ProduktApiData.PRODUKT_IDS)

                .when()
                .get("produkte")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonEquals(ProduktApiData.PRODUKT_DTOS).when(Option.IGNORING_ARRAY_ORDER));
    }
}
