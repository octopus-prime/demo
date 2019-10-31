package com.example.rechnung.service;

import com.example.kunde.api.KundeApiData;
import com.example.preis.api.PreisApiData;
import com.example.produkt.api.ProduktApiData;
import com.example.rechnung.api.RechnungApiData;

import java.util.List;

import static com.example.rechnung.api.RechnungApiData.RECHNUNG_ID;

class RechnungServiceData {

    static final Rechnung RECHNUNG = Rechnung.builder()
            .rechnungId(RECHNUNG_ID)
            .vorname(KundeApiData.KUNDE_DTO.getVorname())
            .nachname(KundeApiData.KUNDE_DTO.getNachname())
            .strasse(KundeApiData.KUNDE_DTO.getRechnungsadresse().getStrasse())
            .hausnummer(KundeApiData.KUNDE_DTO.getRechnungsadresse().getHausnummer())
            .plz(KundeApiData.KUNDE_DTO.getRechnungsadresse().getPlz())
            .wohnort(KundeApiData.KUNDE_DTO.getRechnungsadresse().getWohnort())
            .warenkorb(List.of(
                    Rechnung.Posten.builder()
                            .anzahl(RechnungApiData.BESTELLUNG_DTO.getWarenkorb().get(0).getAnzahl())
                            .produkt(ProduktApiData.PRODUKT1_DTO.getBezeichnung())
                            .preis(PreisApiData.PREIS1_DTO.getAmount())
                            .build(),
                    Rechnung.Posten.builder()
                            .anzahl(RechnungApiData.BESTELLUNG_DTO.getWarenkorb().get(1).getAnzahl())
                            .produkt(ProduktApiData.PRODUKT2_DTO.getBezeichnung())
                            .preis(PreisApiData.PREIS2_DTO.getAmount())
                            .build()
            ))
            .build();
}
