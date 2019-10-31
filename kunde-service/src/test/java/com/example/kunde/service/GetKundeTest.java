package com.example.kunde.service;

import com.example.kunde.api.KundeApiData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GetKundeTest {

    private static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    @BeforeAll
    static void setUp(@LocalServerPort final int port, @Value("${server.servlet.context-path}") final String basePath) {
        SPECIFICATION.port(port).basePath(basePath);
    }

    @BeforeEach
    void setUp() {
        kundeRepository.deleteAll();
    }

    @Test
    @DisplayName("Should give 'Kunde not found' and message")
    void getKundeNotFound() {
        given(SPECIFICATION)
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
    void getKundeOk() {
        adresseRepository.save(KundeServiceData.ADRESSE);
        kundeRepository.save(KundeServiceData.KUNDE);

        given(SPECIFICATION)
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
