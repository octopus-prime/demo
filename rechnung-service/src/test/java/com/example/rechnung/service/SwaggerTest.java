package com.example.rechnung.service;

import io.restassured.http.ContentType;
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
class SwaggerTest {

    @BeforeAll
    static void setUpRA(@LocalServerPort final int port, final RequestSpecification specification) {
        specification.port(port).basePath("rechnung-api");
    }

    @Test
    @DisplayName("Should give 'ok' and swagger-ui")
    void getSwaggerUi(final RequestSpecification specification) {
        given(specification)

                .when()
                .get("swagger-ui.html")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .contentType(ContentType.HTML);
    }

    @Test
    @DisplayName("Should give 'ok' and api-docs")
    void getApiDocs(final RequestSpecification specification) {
        given(specification)

                .when()
                .get("v3/api-docs")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .body("openapi", is("3.0.1"));
    }
}
