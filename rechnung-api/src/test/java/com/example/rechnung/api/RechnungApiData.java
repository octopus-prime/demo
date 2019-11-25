package com.example.rechnung.api;

import com.example.kunde.api.KundeApiData;
import com.example.produkt.api.ProduktApiData;

import java.util.List;
import java.util.UUID;

import static com.example.produkt.api.ProduktApiData.PRODUKT1_ID;
import static com.example.produkt.api.ProduktApiData.PRODUKT2_ID;

public interface RechnungApiData {
    BestellungDto BESTELLUNG_DTO = BestellungDto.builder()
            .kundeId(KundeApiData.KUNDE_ID)
            .warenkorb(List.of(
                    BestellungDto.Posten.builder()
                            .produktId(PRODUKT1_ID)
                            .anzahl(2)
                            .build(),
                    BestellungDto.Posten.builder()
                            .produktId(PRODUKT2_ID)
                            .anzahl(1)
                            .build()
            ))
            .build();
    UUID RECHNUNG_ID = UUID.fromString("2d9f379e-4b5f-4439-ad67-ac9e9cc722d3");
    RechnungDto RECHNUNG_DTO = RechnungDto.builder()
            .rechnungId(RECHNUNG_ID)
            .vorname(KundeApiData.KUNDE_DTO.getVorname())
            .nachname(KundeApiData.KUNDE_DTO.getNachname())
            .strasse(KundeApiData.KUNDE_DTO.getRechnungsadresse().getStrasse())
            .hausnummer(KundeApiData.KUNDE_DTO.getRechnungsadresse().getHausnummer())
            .plz(KundeApiData.KUNDE_DTO.getRechnungsadresse().getPlz())
            .wohnort(KundeApiData.KUNDE_DTO.getRechnungsadresse().getWohnort())
            .warenkorb(List.of(
                    RechnungDto.Posten.builder()
                            .anzahl(RechnungApiData.BESTELLUNG_DTO.getWarenkorb().get(0).getAnzahl())
                            .produkt(ProduktApiData.PRODUKT1_DTO.getBezeichnung())
                            .preis(ProduktApiData.PRODUKT1_DTO.getPreis())
                            .build(),
                    RechnungDto.Posten.builder()
                            .anzahl(RechnungApiData.BESTELLUNG_DTO.getWarenkorb().get(1).getAnzahl())
                            .produkt(ProduktApiData.PRODUKT2_DTO.getBezeichnung())
                            .preis(ProduktApiData.PRODUKT2_DTO.getPreis())
                            .build()
            ))
            .build();
}
