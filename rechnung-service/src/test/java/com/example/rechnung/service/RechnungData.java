package com.example.rechnung.service;

import com.example.rechnung.api.RechnungDto;

import java.util.List;
import java.util.UUID;

class RechnungData {

    static final UUID RECHNUNG_ID = UUID.fromString("2d9f379e-4b5f-4439-ad67-ac9e9cc722d3");
    static final Rechnung RECHNUNG = Rechnung.builder()
            .rechnungId(RECHNUNG_ID)
            .vorname(KundeData.KUNDE_DTO.getVorname())
            .nachname(KundeData.KUNDE_DTO.getNachname())
            .strasse(KundeData.KUNDE_DTO.getRechnungsadresse().getStrasse())
            .hausnummer(KundeData.KUNDE_DTO.getRechnungsadresse().getHausnummer())
            .plz(KundeData.KUNDE_DTO.getRechnungsadresse().getPlz())
            .wohnort(KundeData.KUNDE_DTO.getRechnungsadresse().getWohnort())
            .warenkorb(List.of(
                    Rechnung.Posten.builder()
                            .anzahl(BestellungData.BESTELLUNG_DTO.getWarenkorb().get(0).getAnzahl())
                            .produkt(ProduktData.PRODUKT1_DTO.getBezeichnung())
                            .preis(PreisData.PREIS1_DTO.getAmount())
                            .build(),
                    Rechnung.Posten.builder()
                            .anzahl(BestellungData.BESTELLUNG_DTO.getWarenkorb().get(1).getAnzahl())
                            .produkt(ProduktData.PRODUKT2_DTO.getBezeichnung())
                            .preis(PreisData.PREIS2_DTO.getAmount())
                            .build()
            ))
            .build();
    static final RechnungDto RECHNUNG_DTO = RechnungDto.builder()
            .rechnungId(RECHNUNG_ID)
            .vorname(KundeData.KUNDE_DTO.getVorname())
            .nachname(KundeData.KUNDE_DTO.getNachname())
            .strasse(KundeData.KUNDE_DTO.getRechnungsadresse().getStrasse())
            .hausnummer(KundeData.KUNDE_DTO.getRechnungsadresse().getHausnummer())
            .plz(KundeData.KUNDE_DTO.getRechnungsadresse().getPlz())
            .wohnort(KundeData.KUNDE_DTO.getRechnungsadresse().getWohnort())
            .warenkorb(List.of(
                    RechnungDto.Posten.builder()
                            .anzahl(BestellungData.BESTELLUNG_DTO.getWarenkorb().get(0).getAnzahl())
                            .produkt(ProduktData.PRODUKT1_DTO.getBezeichnung())
                            .preis(PreisData.PREIS1_DTO.getAmount())
                            .build(),
                    RechnungDto.Posten.builder()
                            .anzahl(BestellungData.BESTELLUNG_DTO.getWarenkorb().get(1).getAnzahl())
                            .produkt(ProduktData.PRODUKT2_DTO.getBezeichnung())
                            .preis(PreisData.PREIS2_DTO.getAmount())
                            .build()
            ))
            .build();
}
