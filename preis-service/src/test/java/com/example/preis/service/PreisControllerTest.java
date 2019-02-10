package com.example.preis.service;

import com.example.preis.api.PreisDto;
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
    private static final Set<PreisDto> PREIS_DTOS = Collections.emptySet();

    @InjectMocks
    private PreisController controller;

    @Mock
    private PreisRepository repository;

    @Mock
    private PreisMapper mapper;

    @Test
    @DisplayName("Should give preise")
    void getPreise() {
        when(repository.findAllByProduktIdIn(PRODUKT_IDS)).thenReturn(PREISE);
        when(mapper.map(PREISE)).thenReturn(PREIS_DTOS);

        then(controller.getPreise(PRODUKT_IDS)).isSameAs(PREIS_DTOS);
    }
}
