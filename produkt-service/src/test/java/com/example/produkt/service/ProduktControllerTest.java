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
class ProduktControllerTest {

    @InjectMocks
    private ProduktController controller;

    @Mock
    private ProduktService service;

    @Nested
    class GetProdukte {

        @Test
        @DisplayName("Should give produkte")
        void ok() {
            when(service.getProdukte(PRODUKT_IDS)).thenReturn(PRODUKT_DTOS);

            then(controller.getProdukte(PRODUKT_IDS)).isSameAs(PRODUKT_DTOS);
        }

        @Test
        @DisplayName("Should give empty")
        void notFound() {
            when(service.getProdukte(PRODUKT_IDS)).thenReturn(Set.of());

            then(controller.getProdukte(PRODUKT_IDS)).isEmpty();
        }
    }
}
