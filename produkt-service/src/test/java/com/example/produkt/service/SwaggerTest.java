package com.example.produkt.service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
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
import static org.hamcrest.Matchers.startsWith;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SwaggerTest {

    private static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();

    @BeforeAll
    static void setUp(@LocalServerPort final int port, @Value("${server.servlet.context-path}") final String basePath) {
        SPECIFICATION.port(port).basePath(basePath);
    }

    @Test
    @DisplayName("Should give 'ok' and swagger-ui")
    void getSwaggerUi() {
        given(SPECIFICATION)
                .with()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("swagger-ui.html")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .body(startsWith("<"));
    }

    @Test
    @DisplayName("Should give 'ok' and api-docs")
    void getApiDocs() {
        given(SPECIFICATION)
                .with()
                .accept(MediaType.APPLICATION_JSON_VALUE)

                .when()
                .get("v3/api-docs")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("openapi", startsWith("3."));
    }
}
