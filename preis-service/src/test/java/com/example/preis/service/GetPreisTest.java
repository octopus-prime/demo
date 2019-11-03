package com.example.preis.service;

import com.example.common.RestAssuredExtension;
import com.example.preis.api.PreisApiData;
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
class GetPreisTest {

    @Autowired
    private PreisRepository preisRepository;

    @BeforeEach
    void setUp() {
        preisRepository.deleteAll();
    }

    @Test
    @DisplayName("Should give empty")
    void getPreisNotFound() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("produktIds", PreisApiData.PRODUKT_IDS)

                .when()
                .get("preise")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonEquals(Set.of()));
    }

    @Test
    @DisplayName("Should give 'ok' and preise")
    void getPreisOk() {
        preisRepository.save(PreisServiceData.PREIS1);
        preisRepository.save(PreisServiceData.PREIS2);

        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("produktIds", PreisApiData.PRODUKT_IDS)

                .when()
                .get("preise")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonEquals(PreisApiData.PREIS_DTOS).when(Option.IGNORING_ARRAY_ORDER));
    }
}
