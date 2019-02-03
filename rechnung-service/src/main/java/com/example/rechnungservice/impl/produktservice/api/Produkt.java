package com.example.rechnungservice.impl.produktservice.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Produkt {

    private UUID id;
    private String bezeichnung;
    private String beschreibung;
}
