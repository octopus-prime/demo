package com.example.kunde.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kunde {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
