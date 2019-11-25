package com.example.produkt.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProduktDto {

    private UUID id;
    private String bezeichnung;
    private String beschreibung;
    private BigDecimal preis;
}
