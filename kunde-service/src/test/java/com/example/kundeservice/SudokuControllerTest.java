package com.example.kundeservice;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SudokuControllerTest {

    private RequestSpecification specification;

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
        specification = new RequestSpecBuilder()
                .setBaseUri("http://localhost").setPort(port)
                .setContentType(ContentType.TEXT).setAccept(ContentType.TEXT)
                .addFilter(new RequestLoggingFilter()).addFilter(new ResponseLoggingFilter())
                .build();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", lineSeparator = "-")
    void test(final String in, final int status, final String out) {
        given(specification).body(in)
                .when().post("sudoku")
                .then().statusCode(status)
                .assertThat().body(is(out));
    }
}
