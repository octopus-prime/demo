package com.example.rechnung.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class RechnungDto {

    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class Posten {

        private String produkt;
        private Integer anzahl;
        private Number preis;
    }

    private UUID rechnungId;
    private String vorname;
    private String nachname;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String wohnort;
    private List<Posten> warenkorb;
}
