package com.example.preis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.example.preis.api.PreisApiData.PREIS_DTOS;
import static com.example.preis.api.PreisApiData.PRODUKT_IDS;
import static com.example.preis.service.PreisServiceData.PREISE;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreisServiceTest {

    @InjectMocks
    private PreisService service;

    @Mock
    private PreisRepository repository;

    @Mock
    private PreisMapper mapper;

    @Nested
    class GetPreise {

        @Test
        @DisplayName("Should give preise")
        void ok() {
            when(repository.findAllByProduktIdIn(PRODUKT_IDS)).thenReturn(PREISE);
            when(mapper.map(PREISE)).thenReturn(PREIS_DTOS);

            then(service.getPreise(PRODUKT_IDS)).isSameAs(PREIS_DTOS);
        }

        @Test
        @DisplayName("Should give empty")
        void notFound() {
            when(repository.findAllByProduktIdIn(PRODUKT_IDS)).thenReturn(Set.of());
            when(mapper.map(Set.of())).thenReturn(Set.of());

            then(service.getPreise(PRODUKT_IDS)).isEmpty();
        }
    }
}
