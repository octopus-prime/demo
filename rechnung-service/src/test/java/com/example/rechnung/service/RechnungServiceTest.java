package com.example.rechnung.service;

import com.example.kunde.api.KundeApi;
import com.example.preis.api.PreisApi;
import com.example.produkt.api.ProduktApi;
import com.example.rechnung.api.RechnungDto;
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

//
//    @Test
//    @DisplayName("Should give new rechnung")
//    void createRechnungOk() {
//        when(kundeService.getKunde(KUNDE_ID)).thenReturn(Optional.of(KUNDE_DTO));
//        when(produktService.getProdukt(any())).thenReturn(PRODUKT1_DTO, PRODUKT2_DTO);
//        when(preisService.getPreis(any())).thenReturn(Optional.of(PREIS1_DTO), Optional.of(PREIS2_DTO));
//        when(rechnungRepository.save(any())).then(returnsFirstArg());
//
//        final Rechnung rechnung = rechnungService.createRechnung(BESTELLUNG_DTO);
//
//        verify(produktService).getProdukt(PRODUKT1_ID);
//        verify(produktService).getProdukt(PRODUKT2_ID);
//        verify(preisService).getPreis(PRODUKT1_ID);
//        verify(preisService).getPreis(PRODUKT2_ID);
//
//        assertThat(rechnung.getVorname()).isSameAs(KUNDE_DTO.getVorname());
//        assertThat(rechnung.getNachname()).isSameAs(KUNDE_DTO.getNachname());
//        assertThat(rechnung.getStrasse()).isSameAs(KUNDE_DTO.getRechnungsadresse().getStrasse());
//        assertThat(rechnung.getHausnummer()).isSameAs(KUNDE_DTO.getRechnungsadresse().getHausnummer());
//        assertThat(rechnung.getPlz()).isSameAs(KUNDE_DTO.getRechnungsadresse().getPlz());
//        assertThat(rechnung.getWohnort()).isSameAs(KUNDE_DTO.getRechnungsadresse().getWohnort());
//        assertThat(rechnung.getWarenkorb()).hasSize(2);
//        final Rechnung.Posten posten1 = rechnung.getWarenkorb().get(0);
//        assertThat(posten1.getProdukt()).isSameAs(PRODUKT1_DTO.getBezeichnung());
//        assertThat(posten1.getAnzahl()).isSameAs(BESTELLUNG_DTO.getWarenkorb().get(0).getAnzahl());
//        assertThat(posten1.getPreis()).isSameAs(PREIS1_DTO.getAmount());
//        final Rechnung.Posten posten2 = rechnung.getWarenkorb().get(1);
//        assertThat(posten2.getProdukt()).isSameAs(PRODUKT2_DTO.getBezeichnung());
//        assertThat(posten2.getAnzahl()).isSameAs(BESTELLUNG_DTO.getWarenkorb().get(1).getAnzahl());
//        then(posten2.getPreis()).isSameAs(PREIS2_DTO.getAmount());
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
//        thenThrownBy(() -> rechnungService.createRechnung(BESTELLUNG_DTO))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining(type + " not found");
//    }
//
//    private static Stream<Arguments> data() {
//        return Stream.of(
//                Arguments.of(Optional.empty(), null, "Kunde"),
//                Arguments.of(Optional.of(KUNDE_DTO), null, "Produkt"),
//                Arguments.of(Optional.of(KUNDE_DTO), PRODUKT1_DTO, "Preis")
//        );
//    }

    @Test
    @DisplayName("Should give old rechnung")
    void getRechnungOk() {
        final UUID rechnungId = UUID.randomUUID();
        final Rechnung rechnung = Rechnung.builder().build();
        final RechnungDto rechnungDto = RechnungDto.builder().build();

        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.of(rechnung));
        when(rechnungMapper.map(rechnung)).thenReturn(rechnungDto);

        then(rechnungService.getRechnung(rechnungId)).isSameAs(rechnungDto);
    }

    @Test
    @DisplayName("Should give 'Rechnung not found'")
    void getRechnungNotFound() {
        final UUID rechnungId = UUID.randomUUID();

        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.empty());

        thenThrownBy(() -> rechnungService.getRechnung(rechnungId)).hasMessageContaining("Rechnung not found");
    }
}
