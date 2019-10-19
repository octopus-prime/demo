package com.example.rechnung.service;

import com.example.kunde.api.KundeApi;
import com.example.preis.api.PreisApi;
import com.example.produkt.api.ProduktApi;
import com.example.rechnung.api.RechnungDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

import static com.example.rechnung.service.BestellungData.BESTELLUNG_DTO;
import static com.example.rechnung.service.KundeData.KUNDE_DTO;
import static com.example.rechnung.service.KundeData.KUNDE_ID;
import static com.example.rechnung.service.PreisData.PREIS1_DTO;
import static com.example.rechnung.service.PreisData.PREIS2_DTO;
import static com.example.rechnung.service.ProduktData.*;
import static com.example.rechnung.service.RechnungData.RECHNUNG_DTO;
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
    private PreisApi preisApi;

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
            when(kundeApi.getKunde(KUNDE_ID)).thenReturn(KUNDE_DTO);
            when(produktApi.getProdukte(Set.of(PRODUKT1_ID, PRODUKT2_ID))).thenReturn(Set.of(PRODUKT1_DTO, PRODUKT2_DTO));
            when(preisApi.getPreise(Set.of(PRODUKT1_ID, PRODUKT2_ID))).thenReturn(Set.of(PREIS1_DTO, PREIS2_DTO));
            doAnswer((InvocationOnMock invocation) -> {
                ((Runnable) invocation.getArguments()[0]).run();
                return null;
            }).when(executorService).execute(any());
        }

        @Test
        @DisplayName("Should give new rechnung")
        void ok() {
            final ArgumentCaptor<Rechnung> captor = ArgumentCaptor.forClass(Rechnung.class);
            when(rechnungRepository.save(captor.capture())).then(returnsFirstArg());
            when(rechnungMapper.map(any())).thenReturn(RECHNUNG_DTO);

            then(rechnungService.createRechnung(BESTELLUNG_DTO)).isSameAs(RECHNUNG_DTO);

            final Rechnung rechnung = captor.getValue();
            then(rechnung.getNachname()).isSameAs(KUNDE_DTO.getNachname());
            then(rechnung.getVorname()).isSameAs(KUNDE_DTO.getVorname());
            then(rechnung.getNachname()).isSameAs(KUNDE_DTO.getNachname());
            then(rechnung.getStrasse()).isSameAs(KUNDE_DTO.getRechnungsadresse().getStrasse());
            then(rechnung.getHausnummer()).isSameAs(KUNDE_DTO.getRechnungsadresse().getHausnummer());
            then(rechnung.getPlz()).isSameAs(KUNDE_DTO.getRechnungsadresse().getPlz());
            then(rechnung.getWohnort()).isSameAs(KUNDE_DTO.getRechnungsadresse().getWohnort());
            then(rechnung.getWarenkorb()).hasSize(2);
            final Rechnung.Posten posten1 = rechnung.getWarenkorb().get(0);
            then(posten1.getProdukt()).isSameAs(PRODUKT1_DTO.getBezeichnung());
            then(posten1.getAnzahl()).isSameAs(BESTELLUNG_DTO.getWarenkorb().get(0).getAnzahl());
            then(posten1.getPreis()).isSameAs(PREIS1_DTO.getAmount());
            final Rechnung.Posten posten2 = rechnung.getWarenkorb().get(1);
            then(posten2.getProdukt()).isSameAs(PRODUKT2_DTO.getBezeichnung());
            then(posten2.getAnzahl()).isSameAs(BESTELLUNG_DTO.getWarenkorb().get(1).getAnzahl());
            then(posten2.getPreis()).isSameAs(PREIS2_DTO.getAmount());

            verify(rechnungMapper).map(rechnung);
        }

        @Test
        void notFoundKunde() {
            when(kundeApi.getKunde(KUNDE_ID)).thenReturn(null);

            thenThrownBy(() -> rechnungService.createRechnung(BESTELLUNG_DTO))
                    .isInstanceOf(CompletionException.class)
                    .hasCauseInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Kunde not found");
        }

        @Test
        void notFoundProdukte() {
            when(produktApi.getProdukte(Set.of(PRODUKT1_ID, PRODUKT2_ID))).thenReturn(Set.of());

            thenThrownBy(() -> rechnungService.createRechnung(BESTELLUNG_DTO))
                    .isInstanceOf(CompletionException.class)
                    .hasCauseInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Produkt not found");
        }

        @Test
        void notFoundPreise() {
            when(preisApi.getPreise(Set.of(PRODUKT1_ID, PRODUKT2_ID))).thenReturn(Set.of());

            thenThrownBy(() -> rechnungService.createRechnung(BESTELLUNG_DTO))
                    .isInstanceOf(CompletionException.class)
                    .hasCauseInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Preis not found");
        }
    }

    @Nested
    class GetRechnung {

        private final UUID rechnungId = UUID.randomUUID();

        @Test
        @DisplayName("Should give old rechnung")
        void ok() {
            final Rechnung rechnung = Rechnung.builder().build();
            final RechnungDto rechnungDto = RechnungDto.builder().build();

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
