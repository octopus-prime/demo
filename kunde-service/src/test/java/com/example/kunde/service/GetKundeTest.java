package com.example.kunde.service;

import com.example.common.AutoConfigureRestAssured;
import com.example.kunde.api.KundeApiData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestAssured
class GetKundeTest {

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    @BeforeEach
    void setUp() {
        kundeRepository.deleteAll();
    }

    @Test
    @DisplayName("Should give 'Kunde not found' and message")
    void getKundeNotFound() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("kundeId", KundeApiData.KUNDE_ID)

                .when()
                .get("kunden/{kundeId}")

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("message", is("Kunde not found"));
    }

    @Test
    @DisplayName("Should give 'ok' and kunde")
    void getKundeOk() {
        adresseRepository.save(KundeServiceData.ADRESSE);
        kundeRepository.save(KundeServiceData.KUNDE);

        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("kundeId", KundeApiData.KUNDE_ID)

                .when()
                .get("kunden/{kundeId}")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonEquals(KundeApiData.KUNDE_DTO));
    }
}
