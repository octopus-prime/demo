package com.example.rechnung.service;

import com.example.common.RestAssuredExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(RestAssuredExtension.class)
class ActuatorTest {

    @Test
    @DisplayName("Should give 'ok' and info")
    void getInfo() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)

                .when()
                .get("actuator/info")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(startsWith("{"));
    }

    @Test
    @DisplayName("Should give 'ok' and status 'up'")
    void getHealth() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)

                .when()
                .get("actuator/health")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("status", is("UP"));
    }
}
