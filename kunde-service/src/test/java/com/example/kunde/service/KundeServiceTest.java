package com.example.kunde.service;

import com.example.kunde.api.KundeApiData;
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

        @Test
        @DisplayName("Should give kunde")
        void ok() {
            when(repository.findById(KundeApiData.KUNDE_ID)).thenReturn(Optional.of(KundeServiceData.KUNDE));
            when(mapper.map(KundeServiceData.KUNDE)).thenReturn(KundeApiData.KUNDE_DTO);

            then(service.getKunde(KundeApiData.KUNDE_ID)).isSameAs(KundeApiData.KUNDE_DTO);
        }

        @Test
        @DisplayName("Should throw 'not found'")
        void notFound() {
            when(repository.findById(KundeApiData.KUNDE_ID)).thenReturn(Optional.empty());

            thenThrownBy(() -> service.getKunde(KundeApiData.KUNDE_ID))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Kunde not found")
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }
    }
}
