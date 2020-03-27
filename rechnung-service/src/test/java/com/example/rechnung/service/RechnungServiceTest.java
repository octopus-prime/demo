package com.example.rechnung.service;

import com.example.kunde.api.KundeApi;
import com.example.kunde.api.KundeDto;
import com.example.produkt.api.ProduktApi;
import com.example.produkt.api.ProduktDto;
import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungDto;
import com.example.rechnung.service.RechnungService.NotFound;
import info.solidsoft.mockito.java8.AssertionMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RechnungServiceTest {

    @InjectMocks
    private RechnungService rechnungService;

    @Mock
    private KundeApi kundeApi;

    @Mock
    private ProduktApi produktApi;

    @Mock
    private RechnungRepository rechnungRepository;

    @Mock
    private RechnungMapper rechnungMapper;

    @Mock
    private TraceableExecutorService executorService;

    @Nested
    class CreateRechnung {

        @BeforeEach
        void setUp() {
            doAnswer((InvocationOnMock invocation) -> {
                ((Runnable) invocation.getArguments()[0]).run();
                return null;
            }).when(executorService).execute(any());
        }

        @Test
        @DisplayName("Should give new rechnung")
        void ok(@Mock final BestellungDto bestellungDto, @Mock final KundeDto kundeDto, @Mock final RechnungDto rechnungDto, @Mock final Rechnung rechnung) {
            final Set<ProduktDto> produktDtos = Set.of(
                    ProduktDto.builder().id(UUID.randomUUID()).build(),
                    ProduktDto.builder().id(UUID.randomUUID()).build(),
                    ProduktDto.builder().id(UUID.randomUUID()).build()
            );
            when(kundeApi.getKunde(any())).thenReturn(kundeDto);
            when(produktApi.getProdukte(any())).thenReturn(produktDtos);
            when(rechnungMapper.map(
                    eq(bestellungDto),
                    AssertionMatcher.<Supplier<KundeDto>>assertArg(supplier -> then(supplier.get()).isSameAs(kundeDto)),
                    assertArg(function -> produktDtos.forEach(produktDto -> then(function.apply(produktDto.getId())).isSameAs(produktDto))))
            ).thenReturn(rechnung);
            when(rechnungRepository.save(rechnung)).then(returnsFirstArg());
            when(rechnungMapper.map(rechnung)).thenReturn(rechnungDto);

            then(rechnungService.createRechnung(bestellungDto)).isSameAs(rechnungDto);
            verify(executorService, times(2)).execute(any());
        }

        @Test
        @DisplayName("Should throw 'Kunde not found'")
        void notFoundKunde(@Mock final BestellungDto bestellungDto, @Mock final Set<ProduktDto> produktDtos) {
            when(kundeApi.getKunde(any())).thenReturn(null);
            when(produktApi.getProdukte(any())).thenReturn(produktDtos);
            when(rechnungMapper.map(any(), ArgumentMatchers.<Supplier<KundeDto>>any(), any())).thenThrow(NotFound.KUNDE.get());

            thenThrownBy(() -> rechnungService.createRechnung(bestellungDto))
                    .isInstanceOf(CompletionException.class)
                    .hasCauseInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Kunde not found");

            verifyNoInteractions(rechnungRepository);
            verify(executorService, times(2)).execute(any());
        }

        @Test
        @DisplayName("Should throw 'Produkt not found'")
        void notFoundProdukte(@Mock final BestellungDto bestellungDto, @Mock final KundeDto kundeDto) {
            when(kundeApi.getKunde(any())).thenReturn(kundeDto);
            when(produktApi.getProdukte(any())).thenReturn(Set.of());
            when(rechnungMapper.map(any(), ArgumentMatchers.<Supplier<KundeDto>>any(), any())).thenThrow(NotFound.PRODUKT.get());

            thenThrownBy(() -> rechnungService.createRechnung(bestellungDto))
                    .isInstanceOf(CompletionException.class)
                    .hasCauseInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Produkt not found");

            verifyNoInteractions(rechnungRepository);
            verify(executorService, times(2)).execute(any());
        }
    }

    @Nested
    class GetRechnung {

        private final UUID rechnungId = UUID.randomUUID();

        @Test
        @DisplayName("Should give old rechnung")
        void ok(@Mock final RechnungDto rechnungDto, @Mock final Rechnung rechnung) {
            when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.of(rechnung));
            when(rechnungMapper.map(rechnung)).thenReturn(rechnungDto);
            then(rechnungService.getRechnung(rechnungId)).isSameAs(rechnungDto);
        }

        @Test
        @DisplayName("Should give 'Rechnung not found'")
        void notFound() {
            when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.empty());
            thenThrownBy(() -> rechnungService.getRechnung(rechnungId))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Rechnung not found")
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }
    }
}
