package com.example.rechnung.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.example.rechnung.service.BestellungData.BESTELLUNG;
import static com.example.rechnung.service.RechnungData.RECHNUNG;
import static com.example.rechnung.service.RechnungData.RECHNUNG_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechnungControllerTest {

    private static final ResponseStatusException EXCEPTION = new ResponseStatusException(HttpStatus.NOT_FOUND);

    @InjectMocks
    private RechnungController controller;

    @Mock
    private RechnungService service;

    @Test
    @DisplayName("Should give 'Kunde not found'")
    void createRechnungNotFound() {
        when(service.createRechnung(BESTELLUNG)).thenThrow(EXCEPTION);

        assertThatThrownBy(() -> controller.createRechnung(BESTELLUNG)).isSameAs(EXCEPTION);
    }

    @Test
    @DisplayName("Should give new rechnung")
    void createRechnungOk() {
        when(service.createRechnung(BESTELLUNG)).thenReturn(RECHNUNG);

        assertThat(controller.createRechnung(BESTELLUNG)).isSameAs(RECHNUNG);
    }

    @Test
    @DisplayName("Should give 'Rechnung not found'")
    void getRechnungNotFound() {
        when(service.getRechnung(RECHNUNG_ID)).thenThrow(EXCEPTION);

        assertThatThrownBy(() -> controller.getRechnung(RECHNUNG_ID)).isSameAs(EXCEPTION);
    }

    @Test
    @DisplayName("Should give old rechnung")
    void getRechnungOk() {
        when(service.getRechnung(RECHNUNG_ID)).thenReturn(RECHNUNG);

        assertThat(controller.getRechnung(RECHNUNG_ID)).isSameAs(RECHNUNG);
    }
}
