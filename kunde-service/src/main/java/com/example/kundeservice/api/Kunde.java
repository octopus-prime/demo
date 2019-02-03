package com.example.kundeservice.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Kunde {

    @Data
    @Builder
    public static class Adresse {

        private String strasse;
        private String hausnummer;
        private String plz;
        private String wohnort;
    }

    private UUID id;
    private String vorname;
    private String nachname;
    private Adresse lieferadresse;
    private Adresse rechnungsadresse;
}
