package com.example.rechnung.service;

import com.example.kunde.api.Kunde;

import java.util.UUID;

public class KundeData {

    public static final UUID KUNDE_ID = UUID.fromString("bf73ce21-f91b-4619-8891-1b4b471db3fd");
    public static final Kunde.Adresse ADRESSE = Kunde.Adresse.builder()
            .strasse("str")
            .hausnummer("1c")
            .plz("12345")
            .wohnort("wüste")
            .build();
    public static final Kunde KUNDE = Kunde.builder()
            .id(KUNDE_ID)
            .vorname("foo")
            .nachname("bar")
            .rechnungsadresse(ADRESSE)
            .lieferadresse(ADRESSE)
            .build();
//    public static final Kunde KUNDE = read();
//
//    private static Kunde read() {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.readValue(KundeData.class.getResourceAsStream("/__files/bf73ce21-f91b-4619-8891-1b4b471db3fd.json"), Kunde.class);
//        } catch (final IOException e) {
//            throw new RuntimeException("Kunde data", e);
//        }
//    }
}
