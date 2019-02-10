package com.example.kunde.service;

import com.example.kunde.api.KundeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KundeControllerTest {

    private static final UUID KUNDE_ID = UUID.randomUUID();
    private static final Kunde KUNDE = Kunde.builder().build();
    private static final KundeDto KUNDE_DTO = KundeDto.builder().build();

    @InjectMocks
    private KundeController controller;

    @Mock
    private KundeRepository repository;

    @Mock
    private KundeMapper mapper;

    @Test
    @DisplayName("Should give kunde")
    void getKundeOk() {
        when(repository.findById(KUNDE_ID)).thenReturn(Optional.of(KUNDE));
        when(mapper.map(KUNDE)).thenReturn(KUNDE_DTO);

        then(controller.getKunde(KUNDE_ID)).isSameAs(KUNDE_DTO);
    }

    @Test
    @DisplayName("Should throw 'not found'")
    void getKundeNotFound() {
        when(repository.findById(KUNDE_ID)).thenReturn(Optional.empty());

        thenThrownBy(() -> controller.getKunde(KUNDE_ID)).hasMessageContaining("Kunde not found");
    }
}
