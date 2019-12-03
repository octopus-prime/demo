package com.example.rechnung.service;

import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechnungControllerTest {

    @InjectMocks
    private RechnungController controller;

    @Mock
    private RechnungService service;

    @Nested
    class CreateRechnung {

        @Test
        @DisplayName("Should give 'Kunde not found'")
        void notFound(@Mock final BestellungDto bestellung, @Mock final ResponseStatusException exception) {
            when(service.createRechnung(bestellung)).thenThrow(exception);
            thenThrownBy(() -> controller.createRechnung(bestellung)).isSameAs(exception);
        }

        @Test
        @DisplayName("Should give new rechnung")
        void ok(@Mock final BestellungDto bestellung, @Mock final RechnungDto rechnungDto) {
            when(service.createRechnung(bestellung)).thenReturn(rechnungDto);
            then(controller.createRechnung(bestellung)).isSameAs(rechnungDto);
        }
    }

    @Nested
    class GetRechnung {

        private final UUID rechnungId = UUID.randomUUID();

        @Test
        @DisplayName("Should give 'Rechnung not found'")
        void notFound(@Mock final ResponseStatusException exception) {
            when(service.getRechnung(rechnungId)).thenThrow(exception);
            thenThrownBy(() -> controller.getRechnung(rechnungId)).isSameAs(exception);
        }

        @Test
        @DisplayName("Should give old rechnung")
        void ok(@Mock final RechnungDto rechnungDto) {
            when(service.getRechnung(rechnungId)).thenReturn(rechnungDto);
            then(controller.getRechnung(rechnungId)).isSameAs(rechnungDto);
        }
    }
}
