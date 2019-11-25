package com.example.produkt.service;

import com.example.produkt.api.ProduktApiData;

import java.util.Set;

interface ProduktServiceData {
    Produkt PRODUKT1 = Produkt.builder()
            .id(ProduktApiData.PRODUKT1_ID)
            .bezeichnung(ProduktApiData.PRODUKT1_DTO.getBezeichnung())
            .beschreibung(ProduktApiData.PRODUKT1_DTO.getBeschreibung())
            .preis(ProduktApiData.PRODUKT1_DTO.getPreis())
            .build();
    Produkt PRODUKT2 = Produkt.builder()
            .id(ProduktApiData.PRODUKT2_ID)
            .bezeichnung(ProduktApiData.PRODUKT2_DTO.getBezeichnung())
            .beschreibung(ProduktApiData.PRODUKT2_DTO.getBeschreibung())
            .preis(ProduktApiData.PRODUKT2_DTO.getPreis())
            .build();
    Set<Produkt> PRODUKTE = Set.of(PRODUKT1, PRODUKT2);
}
