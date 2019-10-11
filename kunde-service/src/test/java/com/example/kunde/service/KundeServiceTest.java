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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KundeServiceTest {

    @InjectMocks
    private KundeService service;

    @Mock
    private KundeRepository repository;

    @Mock
    private KundeMapper mapper;

    @Nested
    class GetKunde {

        private final UUID kundeId = UUID.randomUUID();

        @Test
        @DisplayName("Should give kunde")
        void ok() {
            final Kunde kunde = Kunde.builder().build();
            final KundeDto kundeDto = KundeDto.builder().build();

            when(repository.findById(kundeId)).thenReturn(Optional.of(kunde));
            when(mapper.map(kunde)).thenReturn(kundeDto);

            then(service.getKunde(kundeId)).isSameAs(kundeDto);
        }

        @Test
        @DisplayName("Should throw 'not found'")
        void notFound() {
            when(repository.findById(kundeId)).thenReturn(Optional.empty());

            thenThrownBy(() -> service.getKunde(kundeId))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Kunde not found")
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }
    }
}
