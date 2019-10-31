package com.example.kunde.service;

import com.example.kunde.api.KundeApiData;

import java.util.UUID;

interface KundeServiceData {
    Adresse ADRESSE = Adresse.builder()
            .id(UUID.randomUUID())
            .strasse(KundeApiData.ADRESSE_DTO.getStrasse())
            .hausnummer(KundeApiData.ADRESSE_DTO.getHausnummer())
            .plz(KundeApiData.ADRESSE_DTO.getPlz())
            .wohnort(KundeApiData.ADRESSE_DTO.getWohnort())
            .build();
    Kunde KUNDE = Kunde.builder()
            .id(KundeApiData.KUNDE_ID)
            .vorname(KundeApiData.KUNDE_DTO.getVorname())
            .nachname(KundeApiData.KUNDE_DTO.getNachname())
            .rechnungsadresse(ADRESSE)
            .lieferadresse(ADRESSE)
            .build();
}
