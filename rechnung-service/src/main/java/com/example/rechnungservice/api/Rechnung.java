package com.example.rechnungservice.api;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Document
public class Rechnung {

    @Value
    @Builder
    public static class Posten {

        private final String produkt;
        private final Integer anzahl;
        private final Number preis;
    }

    @Id
    private final UUID rechnungId;
    private final String vorname;
    private final String nachname;
    private final String strasse;
    private final String hausnummer;
    private final String plz;
    private final String wohnort;
    private final List<Posten> warenkorb;
}
