package com.example.kunde.service;

import com.example.common.RestAssuredExtension;
import com.example.kunde.api.KundeApiData;
import io.restassured.specification.RequestSpecification;
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

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(RestAssuredExtension.class)
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
    void getKundeNotFound(final RequestSpecification specification) {
        given(specification)
                .with()
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
    void getKundeOk(final RequestSpecification specification) {
        adresseRepository.save(KundeServiceData.ADRESSE);
        kundeRepository.save(KundeServiceData.KUNDE);

        given(specification)
                .with()
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
