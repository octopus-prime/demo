package com.example.rechnungservice.impl.produktservice;

import com.example.rechnungservice.impl.produktservice.api.Produkt;

import java.util.UUID;

public class ProduktData {

    public static final UUID PRODUKT1_ID = UUID.fromString("2d9f379e-4b5f-4439-ad67-ac9e9cc722d3");
    public static final Produkt PRODUKT1 = Produkt.builder()
            .id(PRODUKT1_ID)
            .bezeichnung("foo")
            .beschreibung("bar")
            .build();

    public static final UUID PRODUKT2_ID = UUID.fromString("5115600f-30dd-4f67-92d8-4ecbe7e11d0f");
    public static final Produkt PRODUKT2 = Produkt.builder()
            .id(PRODUKT2_ID)
            .bezeichnung("foo")
            .beschreibung("bar")
            .build();
}
