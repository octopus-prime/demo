package com.example.rechnung.service;

import com.example.common.RestAssuredExtension;
import com.example.kunde.api.KundeApiData;
import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungApiData;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
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
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static com.example.kunde.api.KundeApiData.KUNDE_DTO;
import static com.example.kunde.api.KundeApiData.KUNDE_ID;
import static com.example.preis.api.PreisApiData.PREIS_DTOS;
import static com.example.produkt.api.ProduktApiData.PRODUKT_DTOS;
import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(RestAssuredExtension.class)
@AutoConfigureWireMock(port = 9999)
class CreateRechnungTest {

    @Autowired
    private RechnungRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        givenThat(get(urlPathEqualTo("/kunde-api/kunden/" + KUNDE_ID))
                .willReturn(okForJson(KUNDE_DTO)));
        givenThat(get(urlPathEqualTo("/produkt-api/produkte"))
                .willReturn(okForJson(PRODUKT_DTOS)));
        givenThat(get(urlPathEqualTo("/preis-api/preise"))
                .willReturn(okForJson(PREIS_DTOS)));
    }

    @Test
    @DisplayName("Should give 'bad request' and message")
    void createRechnungBadRequest() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(BestellungDto.builder().build())

                .when()
                .post("rechnungen")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("message", startsWith("Validation failed for object='bestellungDto'."));

        then(repository.count()).isEqualTo(0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("notFoundData")
    @DisplayName("Should give 'not found' and message")
    void createRechnungNotFound(final String type, final String path, final ResponseDefinitionBuilder response) {
        givenThat(get(urlPathEqualTo(path))
                .willReturn(response));

        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(RechnungApiData.BESTELLUNG_DTO)

                .when()
                .post("rechnungen")

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("message", is(type + " not found"));

        then(repository.count()).isEqualTo(0);
    }

    static Stream<Arguments> notFoundData() {
        return Stream.of(
                Arguments.of("Kunde", "/kunde-api/kunden/" + KundeApiData.KUNDE_ID, notFound()),
                Arguments.of("Produkt", "/produkt-api/produkte", okJson("[]")),
                Arguments.of("Preis", "/preis-api/preise", okJson("[]"))
        );
    }

    @Test
    @DisplayName("Should give 'created' and new rechnung")
    void createRechnungOk() {
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(RechnungApiData.BESTELLUNG_DTO)

                .when()
                .post("rechnungen")

                .then()
                .statusCode(HttpStatus.CREATED.value())

                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonEquals(RechnungApiData.RECHNUNG_DTO).whenIgnoringPaths("rechnungId"));

        then(repository.count()).isEqualTo(1);
    }
}
