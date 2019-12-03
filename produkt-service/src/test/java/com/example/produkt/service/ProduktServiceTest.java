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
        void ok(@Mock final Set<UUID> produktIds, @Mock final Set<ProduktDto> produktDtos, @Mock final Set<Produkt> produkte) {
            when(repository.findAllByIdIn(produktIds)).thenReturn(produkte);
            when(mapper.map(produkte)).thenReturn(produktDtos);
            then(service.getProdukte(produktIds)).isSameAs(produktDtos);
        }

        @Test
        @DisplayName("Should give empty")
        void notFound(@Mock final Set<UUID> produktIds) {
            when(repository.findAllByIdIn(produktIds)).thenReturn(Set.of());
            when(mapper.map(Set.of())).thenReturn(Set.of());
            then(service.getProdukte(produktIds)).isEmpty();
        }
    }
}
