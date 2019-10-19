package com.example.rechnung.service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ActuatorTest {

    private final RequestSpecification specification = new RequestSpecBuilder()
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();

    @BeforeEach
    void setUp(@LocalServerPort final int port, @Value("${server.servlet.context-path}") final String contextPath) {
        specification.port(port).basePath(contextPath);
    }

    @Test
    @DisplayName("Should give 'ok' and info")
    void getInfo() {
        given(specification)

                .when()
                .get("actuator/info")

                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should give 'ok' and status 'up'")
    void getHealth() {
        given(specification)

                .when()
                .get("actuator/health")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .body("status", is("UP"));
    }
}
