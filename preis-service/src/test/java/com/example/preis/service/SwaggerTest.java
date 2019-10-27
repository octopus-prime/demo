package com.example.preis.service;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.startsWith;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SwaggerTest {

    private final RequestSpecification specification = new RequestSpecBuilder()
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();

    @BeforeEach
    void setUp(@LocalServerPort final int port, @Value("${server.servlet.context-path}") final String contextPath) {
        specification.port(port).basePath(contextPath);
    }

    @Test
    @DisplayName("Should give 'ok' and swagger-ui")
    void getSwaggerUi() {
        given(specification)

                .when()
                .accept(MediaType.TEXT_HTML_VALUE)
                .get("swagger-ui.html")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_HTML_VALUE);
    }

    @Test
    @DisplayName("Should give 'ok' and api-docs")
    void getApiDocs() {
        given(specification)

                .when()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .get("v3/api-docs")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("openapi", startsWith("3."));
    }
}
