package com.example.rechnung.api;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RechnungDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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
