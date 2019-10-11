package com.example.kunde.service;

import com.example.kunde.api.KundeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KundeControllerTest {

    @InjectMocks
    private KundeController controller;

    @Mock
    private KundeService service;

    @Nested
    class GetKunde {

        private final UUID kundeId = UUID.randomUUID();

        @Test
        @DisplayName("Should give kunde")
        void ok() {
            final KundeDto kundeDto = KundeDto.builder().build();

            when(service.getKunde(kundeId)).thenReturn(kundeDto);

            then(controller.getKunde(kundeId)).isSameAs(kundeDto);
        }

        @Test
        @DisplayName("Should throw 'not found'")
        void notFound() {
            final ResponseStatusException exception = new ResponseStatusException(HttpStatus.NOT_FOUND);

            when(service.getKunde(kundeId)).thenThrow(exception);

            thenThrownBy(() -> controller.getKunde(kundeId)).isSameAs(exception);
        }
    }
}
