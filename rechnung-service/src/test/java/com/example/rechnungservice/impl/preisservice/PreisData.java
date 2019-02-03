package com.example.rechnungservice.impl.preisservice;

import com.example.rechnungservice.impl.preisservice.api.Preis;

public class PreisData {

    public static final Preis PREIS1 = Preis.builder()
            .amount(123.45)
//            .currency("€")
            .build();

    public static final Preis PREIS2 = Preis.builder()
            .amount(123.45)
//            .currency("€")
            .build();
}
