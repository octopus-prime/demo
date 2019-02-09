package com.example.preisservice.impl;

import com.example.preisservice.api.Preis;
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
class PreisControllerTest {

    private static final Set<UUID> PRODUKT_IDS = Collections.emptySet();
    private static final Set<Preis> PREISE = Collections.emptySet();

    @InjectMocks
    private PreisController controller;

    @Mock
    private PreisRepository repository;

    @Test
    @DisplayName("Should give preise")
    void getPreise() {
        when(repository.findAllByProduktIdIn(PRODUKT_IDS)).thenReturn(PREISE);

        then(controller.getPreise(PRODUKT_IDS)).isSameAs(PREISE);
    }
}
