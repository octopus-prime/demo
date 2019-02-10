package com.example.kunde.api;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KundeDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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
