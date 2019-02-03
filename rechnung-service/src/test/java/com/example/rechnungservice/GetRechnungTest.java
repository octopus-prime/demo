package com.example.rechnungservice;

import com.example.rechnungservice.api.Rechnung;
import com.example.rechnungservice.impl.RechnungRepository;
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

import static com.example.rechnungservice.impl.RechnungData.RECHNUNG;
import static com.example.rechnungservice.impl.RechnungData.RECHNUNG_ID;
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
                .get("rechnungen/" + RECHNUNG_ID)

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())

                .assertThat()
                .body("message", is("Rechnung not found"));
    }

    @Test
    @DisplayName("Should give 'ok' and old rechnung")
    void getRechnungOk(final RequestSpecification specification) {
        repository.save(RECHNUNG);

        final Rechnung rechnung = given(specification)

                .when()
                .get("rechnungen/" + RECHNUNG_ID)

                .then()
                .statusCode(HttpStatus.OK.value())

                .extract()
                .as(Rechnung.class);

        assertThat(rechnung).isEqualTo(RECHNUNG);
    }
}
