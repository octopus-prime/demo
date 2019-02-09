package com.example.rechnungservice.impl;

import com.example.produkt.api.ProduktApi;
import com.example.rechnungservice.api.Rechnung;
import com.example.rechnungservice.impl.kundeservice.api.KundeApi;
import com.example.rechnungservice.impl.preisservice.api.PreisApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechnungServiceTest {

    @InjectMocks
    private RechnungService rechnungService;

    @Mock
    private KundeApi kundeService;

    @Mock
    private ProduktApi produktService;

    @Mock
    private PreisApi preisService;

    @Mock
    private RechnungRepository rechnungRepository;
//
//    @Test
//    @DisplayName("Should give new rechnung")
//    void createRechnungOk() {
//        when(kundeService.getKunde(KUNDE_ID)).thenReturn(Optional.of(KUNDE));
//        when(produktService.getProdukt(any())).thenReturn(PRODUKT1, PRODUKT2);
//        when(preisService.getPreis(any())).thenReturn(Optional.of(PREIS1), Optional.of(PREIS2));
//        when(rechnungRepository.save(any())).then(returnsFirstArg());
//
//        final Rechnung rechnung = rechnungService.createRechnung(BESTELLUNG);
//
//        verify(produktService).getProdukt(PRODUKT1_ID);
//        verify(produktService).getProdukt(PRODUKT2_ID);
//        verify(preisService).getPreis(PRODUKT1_ID);
//        verify(preisService).getPreis(PRODUKT2_ID);
//
//        assertThat(rechnung.getVorname()).isSameAs(KUNDE.getVorname());
//        assertThat(rechnung.getNachname()).isSameAs(KUNDE.getNachname());
//        assertThat(rechnung.getStrasse()).isSameAs(KUNDE.getRechnungsadresse().getStrasse());
//        assertThat(rechnung.getHausnummer()).isSameAs(KUNDE.getRechnungsadresse().getHausnummer());
//        assertThat(rechnung.getPlz()).isSameAs(KUNDE.getRechnungsadresse().getPlz());
//        assertThat(rechnung.getWohnort()).isSameAs(KUNDE.getRechnungsadresse().getWohnort());
//        assertThat(rechnung.getWarenkorb()).hasSize(2);
//        final Rechnung.Posten posten1 = rechnung.getWarenkorb().get(0);
//        assertThat(posten1.getProdukt()).isSameAs(PRODUKT1.getBezeichnung());
//        assertThat(posten1.getAnzahl()).isSameAs(BESTELLUNG.getWarenkorb().get(0).getAnzahl());
//        assertThat(posten1.getPreis()).isSameAs(PREIS1.getAmount());
//        final Rechnung.Posten posten2 = rechnung.getWarenkorb().get(1);
//        assertThat(posten2.getProdukt()).isSameAs(PRODUKT2.getBezeichnung());
//        assertThat(posten2.getAnzahl()).isSameAs(BESTELLUNG.getWarenkorb().get(1).getAnzahl());
//        then(posten2.getPreis()).isSameAs(PREIS2.getAmount());
//    }
//
//    @ParameterizedTest(name = "{2}")
//    @MethodSource("data")
//    @DisplayName("Should give 'not found'")
//    void createRechnungNotFound(final Optional<Kunde> kunde, final Produkt produkt, final String type) {
//        when(kundeService.getKunde(KUNDE_ID)).thenReturn(kunde);
//        kunde.ifPresent(k -> {
//            when(produktService.getProdukt(PRODUKT1_ID)).thenReturn(produkt);
//            Optional.ofNullable(produkt).ifPresent(p -> when(preisService.getPreis(PRODUKT1_ID)).thenReturn(Optional.empty()));
//        });
//
//        thenThrownBy(() -> rechnungService.createRechnung(BESTELLUNG))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining(type + " not found");
//    }
//
//    private static Stream<Arguments> data() {
//        return Stream.of(
//                Arguments.of(Optional.empty(), null, "Kunde"),
//                Arguments.of(Optional.of(KUNDE), null, "Produkt"),
//                Arguments.of(Optional.of(KUNDE), PRODUKT1, "Preis")
//        );
//    }

    @Test
    @DisplayName("Should give old rechnung")
    void getRechnungOk() {
        final UUID rechnungId = UUID.randomUUID();
        final Rechnung rechnung = Rechnung.builder().build();

        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.of(rechnung));

        then(rechnungService.getRechnung(rechnungId)).isSameAs(rechnung);
    }

    @Test
    @DisplayName("Should give 'Rechnung not found'")
    void getRechnungNotFound() {
        final UUID rechnungId = UUID.randomUUID();

        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rechnungService.getRechnung(rechnungId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Rechnung not found");
    }
}
