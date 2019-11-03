package com.example.rechnung.service;

import com.example.common.RestAssuredExtension;
import com.example.rechnung.api.RechnungApiData;
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
class GetRechnungTest {

    @Autowired
    private RechnungRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should give 'Rechnung not found' and message")
    void getRechnungNotFound(final RequestSpecification specification) {
        given(specification)
                .with()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("rechnungId", RechnungApiData.RECHNUNG_ID)

                .when()
                .get("rechnungen/{rechnungId}")

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("message", is("Rechnung not found"));
    }

    @Test
    @DisplayName("Should give 'ok' and old rechnung")
    void getRechnungOk(final RequestSpecification specification) {
        repository.save(RechnungServiceData.RECHNUNG);

        given(specification)
                .with()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("rechnungId", RechnungApiData.RECHNUNG_ID)

                .when()
                .get("rechnungen/{rechnungId}")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonEquals(RechnungApiData.RECHNUNG_DTO));
    }
}
