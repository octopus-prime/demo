package com.example.rechnung.service;

import com.example.kunde.api.AdresseDto;
import com.example.kunde.api.KundeDto;

import java.util.UUID;

class KundeData {

    static final UUID KUNDE_ID = UUID.fromString("bf73ce21-f91b-4619-8891-1b4b471db3fd");
    private static final AdresseDto ADRESSE = AdresseDto.builder()
            .strasse("Musterstrasse")
            .hausnummer("17a")
            .plz("12345")
            .wohnort("Musterstadt")
            .build();
    static final KundeDto KUNDE_DTO = KundeDto.builder()
            .id(KUNDE_ID)
            .vorname("Max")
            .nachname("Mustermann")
            .rechnungsadresse(ADRESSE)
            .lieferadresse(ADRESSE)
            .build();
}
