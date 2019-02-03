package com.example.rechnungservice;

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

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestAssuredExtension.class)
//@AutoConfigureWireMock(port = 8888)
class RechnungServiceApplicationTests {

    @BeforeAll
    static void setUpRA(@LocalServerPort final int port, final RequestSpecification specification) {
        specification.port(port);
    }

//    @BeforeEach
//    void setUpWM() {
//        reset();
//    }

//    @ParameterizedTest
//    @ValueSource(strings = {"/kunden", "/produkte", "/preise"})
//    @DisplayName("Should give 'service unavailable' and status 'down'")
//    void getHealthDown(final String path, final RequestSpecification specification) {
//        stubFor(get(path + "/actuator/info").willReturn(notFound()));
//
//        given(specification)
//
//                .when()
//                .get("actuator/health")
//
//                .then()
//                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
//
//                .assertThat()
//                .body("status", is("DOWN"));
//    }

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
                .get("v2/api-docs")

                .then()
                .statusCode(HttpStatus.OK.value())

                .assertThat()
                .body("swagger", is("2.0"));
    }
}
