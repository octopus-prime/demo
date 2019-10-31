package com.example.rechnung.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.example.rechnung.api.RechnungApiData.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechnungControllerTest {

    private static final ResponseStatusException EXCEPTION = new ResponseStatusException(HttpStatus.NOT_FOUND);

    @InjectMocks
    private RechnungController controller;

    @Mock
    private RechnungService service;

    @Nested
    class CreateRechnung {

        @Test
        @DisplayName("Should give 'Kunde not found'")
        void notFound() {
            when(service.createRechnung(BESTELLUNG_DTO)).thenThrow(EXCEPTION);

            thenThrownBy(() -> controller.createRechnung(BESTELLUNG_DTO)).isSameAs(EXCEPTION);
        }

        @Test
        @DisplayName("Should give new rechnung")
        void ok() {
            when(service.createRechnung(BESTELLUNG_DTO)).thenReturn(RECHNUNG_DTO);

            then(controller.createRechnung(BESTELLUNG_DTO)).isSameAs(RECHNUNG_DTO);
        }
    }

    @Nested
    class GetRechnung {

        @Test
        @DisplayName("Should give 'Rechnung not found'")
        void notFound() {
            when(service.getRechnung(RECHNUNG_ID)).thenThrow(EXCEPTION);

            thenThrownBy(() -> controller.getRechnung(RECHNUNG_ID)).isSameAs(EXCEPTION);
        }

        @Test
        @DisplayName("Should give old rechnung")
        void ok() {
            when(service.getRechnung(RECHNUNG_ID)).thenReturn(RECHNUNG_DTO);

            then(controller.getRechnung(RECHNUNG_ID)).isSameAs(RECHNUNG_DTO);
        }
    }
}
