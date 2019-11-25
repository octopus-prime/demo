package com.example.rechnung.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Document
class Rechnung {

    @Data
    @SuperBuilder
    @NoArgsConstructor
    static class Posten {

        private String produkt;
        private Integer anzahl;
        private BigDecimal preis;
    }

    @Id
    private UUID rechnungId;
    private String vorname;
    private String nachname;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String wohnort;
    private List<Posten> warenkorb;
}
