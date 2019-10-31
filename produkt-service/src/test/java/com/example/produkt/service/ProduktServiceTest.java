package com.example.produkt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.example.produkt.api.ProduktApiData.PRODUKT_DTOS;
import static com.example.produkt.api.ProduktApiData.PRODUKT_IDS;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProduktServiceTest {

    @InjectMocks
    private ProduktService service;

    @Mock
    private ProduktRepository repository;

    @Mock
    private ProduktMapper mapper;

    @Nested
    class GetProdukte {

        @Test
        @DisplayName("Should give produkte")
        void ok() {
            when(repository.findAllByIdIn(PRODUKT_IDS)).thenReturn(ProduktServiceData.PRODUKTE);
            when(mapper.map(ProduktServiceData.PRODUKTE)).thenReturn(PRODUKT_DTOS);

            then(service.getProdukte(PRODUKT_IDS)).isSameAs(PRODUKT_DTOS);
        }

        @Test
        @DisplayName("Should give empty")
        void notFound() {
            when(repository.findAllByIdIn(PRODUKT_IDS)).thenReturn(Set.of());
            when(mapper.map(Set.of())).thenReturn(Set.of());

            then(service.getProdukte(PRODUKT_IDS)).isEmpty();
        }
    }
}
