package com.example.rechnung.service;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(RestAssuredExtension.class)
class ActuatorTest {

    @BeforeAll
    static void setUpRA(@LocalServerPort final int port, final RequestSpecification specification) {
        specification.port(port).basePath("rechnung-api");
    }

    @Test
    @DisplayName("Should give 'ok' and info")
    void getInfo(final RequestSpecification specification) {
        given(specification)

                .when()
                .get("actuator/info")

                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should give 'ok' and status 'up'")
    void getHealthUp(final RequestSpecification specification) {
        given(specification)

                .when()
                .get("actuator/health")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .body("status", is("UP"));
    }
}
