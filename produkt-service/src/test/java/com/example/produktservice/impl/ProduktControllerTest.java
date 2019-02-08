package com.example.produktservice.impl;

import com.example.produktservice.api.Produkt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProduktControllerTest {

    private static final Set<UUID> PRODUKT_IDS = Collections.emptySet();
    private static final Set<Produkt> PRODUKTE = Collections.emptySet();

    @InjectMocks
    private ProduktController controller;

    @Mock
    private ProduktRepository repository;

    @Test
    @DisplayName("Should give produkte")
    void getProdukte() {
        when(repository.findAllByIdIn(PRODUKT_IDS)).thenReturn(PRODUKTE);

        then(controller.getProdukte(PRODUKT_IDS)).isSameAs(PRODUKTE);
    }
}
