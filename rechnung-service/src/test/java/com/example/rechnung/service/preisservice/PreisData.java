package com.example.rechnung.service.preisservice;

import com.example.preis.api.Preis;

import java.util.UUID;

public class PreisData {

    public static final Preis PREIS1 = Preis.builder()
            .id(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
            .produktId(UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811"))
            .amount(2313.39)
            .currency("€")
            .build();
    public static final Preis PREIS2 = Preis.builder()
            .id(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
            .produktId(UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f"))
            .amount(1199.99)
            .currency("€")
            .build();
}
