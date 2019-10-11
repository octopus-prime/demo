package com.example.produkt.service;

import com.example.produkt.api.ProduktDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

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

        private final Set<UUID> produktIds = Set.of();

        @Test
        @DisplayName("Should give produkte")
        void ok() {
            final Set<ProduktDto> produktDtos = Set.of();

            when(service.getProdukte(produktIds)).thenReturn(produktDtos);

            then(controller.getProdukte(produktIds)).isSameAs(produktDtos);
        }
    }
}
