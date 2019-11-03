package com.example.produkt.service;

import com.example.common.AutoConfigureRestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestAssured
class SwaggerTest {

    @Test
    @DisplayName("Should give 'ok' and swagger-ui")
    void getSwaggerUi() {
        given()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("swagger-ui.html")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .body(containsString("<title>Swagger UI</title>"));
    }

    @Test
    @DisplayName("Should give 'ok' and api-docs")
    void getApiDocs() {
        given()
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
