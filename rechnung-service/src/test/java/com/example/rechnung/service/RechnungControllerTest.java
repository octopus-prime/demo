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

        @Mock
        private BestellungDto bestellung;

        @Test
        @DisplayName("Should delegate rechnung")
        void success(@Mock final RechnungDto rechnungDto) {
            when(service.createRechnung(bestellung)).thenReturn(rechnungDto);
            then(controller.createRechnung(bestellung)).isSameAs(rechnungDto);
        }

        @Test
        @DisplayName("Should delegate exception")
        void failure(@Mock final RuntimeException exception) {
            when(service.createRechnung(bestellung)).thenThrow(exception);
            thenThrownBy(() -> controller.createRechnung(bestellung)).isSameAs(exception);
        }
    }

    @Nested
    class GetRechnung {

        private final UUID rechnungId = UUID.randomUUID();

        @Test
        @DisplayName("Should delegate rechnung")
        void success(@Mock final RechnungDto rechnungDto) {
            when(service.getRechnung(rechnungId)).thenReturn(rechnungDto);
            then(controller.getRechnung(rechnungId)).isSameAs(rechnungDto);
        }

        @Test
        @DisplayName("Should delegate exception")
        void failure(@Mock final RuntimeException exception) {
            when(service.getRechnung(rechnungId)).thenThrow(exception);
            thenThrownBy(() -> controller.getRechnung(rechnungId)).isSameAs(exception);
        }
    }
}
