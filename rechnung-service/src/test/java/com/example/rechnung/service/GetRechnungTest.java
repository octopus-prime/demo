package com.example.rechnung.service;

import com.example.rechnung.api.Rechnung;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestAssuredExtension.class)
class GetRechnungTest {

    @Autowired
    private RechnungRepository repository;

    @BeforeAll
    static void setUpRA(@LocalServerPort final int port, final RequestSpecification specification) {
        specification.port(port);
    }

    @BeforeEach
    void setUpDB() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should give 'Rechnung not found' and message")
    void getRechnungNotFound(final RequestSpecification specification) {
        given(specification)

                .when()
                .get("rechnungen/" + RechnungData.RECHNUNG_ID)

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())

                .assertThat()
                .body("message", is("Rechnung not found"));
    }

    @Test
    @DisplayName("Should give 'ok' and old rechnung")
    void getRechnungOk(final RequestSpecification specification) {
        repository.save(RechnungData.RECHNUNG);

        final Rechnung rechnung = given(specification)

                .when()
                .get("rechnungen/" + RechnungData.RECHNUNG_ID)

                .then()
                .statusCode(HttpStatus.OK.value())

                .extract()
                .as(Rechnung.class);

        assertThat(rechnung).isEqualTo(RechnungData.RECHNUNG);
    }
}
