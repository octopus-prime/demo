package com.example.rechnung.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

        @NotEmpty
        private String produkt;

        @Positive
        private Integer anzahl;

        @Positive
        private BigDecimal preis;
    }

    @Id
    private UUID rechnungId;

    @NotEmpty
    private String vorname;

    @NotEmpty
    private String nachname;

    @NotEmpty
    private String strasse;

    @NotEmpty
    private String hausnummer;

    @NotEmpty
    private String plz;

    @NotEmpty
    private String wohnort;

    @NotNull
    @Valid
    private List<Posten> warenkorb;
}
