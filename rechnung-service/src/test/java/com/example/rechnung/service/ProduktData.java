package com.example.rechnung.service;

import com.example.produkt.api.ProduktDto;

import java.util.UUID;

public class ProduktData {

    public static final UUID PRODUKT1_ID = UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811");
    public static final ProduktDto PRODUKT1 = ProduktDto.builder()
            .id(PRODUKT1_ID)
            .bezeichnung("foo")
            .beschreibung("bar")
            .build();

    public static final UUID PRODUKT2_ID = UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f");
    public static final ProduktDto PRODUKT2 = ProduktDto.builder()
            .id(PRODUKT2_ID)
            .bezeichnung("foo")
            .beschreibung("bar")
            .build();
}
