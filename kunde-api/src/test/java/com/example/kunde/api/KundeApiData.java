package com.example.kunde.api;

import java.util.UUID;

public interface KundeApiData {

    UUID KUNDE_ID = UUID.fromString("bf73ce21-f91b-4619-8891-1b4b471db3fd");
    AdresseDto ADRESSE_DTO = AdresseDto.builder()
            .strasse("Musterstrasse")
            .hausnummer("17a")
            .plz("12345")
            .wohnort("Musterstadt")
            .build();
    KundeDto KUNDE_DTO = KundeDto.builder()
            .id(KUNDE_ID)
            .vorname("Max")
            .nachname("Mustermann")
            .rechnungsadresse(ADRESSE_DTO)
            .lieferadresse(ADRESSE_DTO)
            .build();
}
