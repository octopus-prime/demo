package com.example.preis.service;

import com.example.preis.api.PreisDto;
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
class PreisControllerTest {

    @InjectMocks
    private PreisController controller;

    @Mock
    private PreisService service;

    @Nested
    class GetPreise {

        private final Set<UUID> produktIds = Set.of();

        @Test
        @DisplayName("Should give preise")
        void ok() {
            final Set<PreisDto> preisDtos = Set.of();

            when(service.getPreise(produktIds)).thenReturn(preisDtos);

            then(controller.getPreise(produktIds)).isSameAs(preisDtos);
        }
    }
}
