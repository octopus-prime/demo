package com.example.rechnung.service;

import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungDto;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Set;
import java.util.stream.Stream;

import static com.example.rechnung.service.ProduktData.PRODUKT1_DTO;
import static com.example.rechnung.service.ProduktData.PRODUKT2_DTO;
import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestAssuredExtension.class)
@AutoConfigureWireMock(port = 9999)
class CreateRechnungTest {

    @Autowired
    private RechnungRepository repository;

    @BeforeAll
    static void setUpRA(@LocalServerPort final int port, final RequestSpecification specification) {
        specification
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    @BeforeEach
    void setUpDB() {
        repository.deleteAll();
    }

    @BeforeEach
    void setUpWM() {
        givenThat(get(urlPathEqualTo("/kunden/" + KundeData.KUNDE_ID)).willReturn(okForJson(KundeData.KUNDE_DTO)));
        givenThat(get(urlPathEqualTo("/produkte")).willReturn(okForJson(Set.of(PRODUKT1_DTO, PRODUKT2_DTO))));
        givenThat(get(urlPathEqualTo("/preise")).willReturn(okForJson(Set.of(PreisData.PREIS1_DTO, PreisData.PREIS2_DTO))));
    }

    @Test
    @DisplayName("Should give 'bad request' and message")
    void createRechnungBadRequest(final RequestSpecification specification) {
        given(specification)
                .with()
                .body(BestellungDto.builder().build())

                .when()
                .post("rechnungen")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())

                .assertThat()
                .body("message", startsWith("Validation failed for object='bestellungDto'."));

        then(repository.count()).isEqualTo(0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("notFoundData")
    @DisplayName("Should give 'not found' and message")
    void createRechnungNotFound(final String type, final String path, final ResponseDefinitionBuilder response, final RequestSpecification specification) {
        givenThat(get(urlPathEqualTo(path)).willReturn(response));

        given(specification)
                .with()
                .body(BestellungData.BESTELLUNG_DTO)

                .when()
                .post("rechnungen")

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())

                .assertThat()
                .body("message", is(type + " not found"));

        then(repository.count()).isEqualTo(0);
    }

    static Stream<Arguments> notFoundData() {
        return Stream.of(
                Arguments.of("Kunde", "/kunden/" + KundeData.KUNDE_ID, notFound()),
                Arguments.of("Produkt", "/produkte", okJson("[]")),
                Arguments.of("Preis", "/preise", okJson("[]"))
        );
    }

    @Test
    @DisplayName("Should give 'created' and new rechnung")
    void createRechnungOk(final RequestSpecification specification) {
        final RechnungDto rechnung = given(specification)
                .with()
                .body(BestellungData.BESTELLUNG_DTO)

                .when()
                .post("rechnungen")

                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)

                .extract()
                .as(RechnungDto.class);

        then(repository.count()).isEqualTo(1);
    }
}
