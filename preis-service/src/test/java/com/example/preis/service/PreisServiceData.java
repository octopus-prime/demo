package com.example.preis.service;

import com.example.preis.api.PreisApiData;

import java.util.Set;

interface PreisServiceData {
    Preis PREIS1 = Preis.builder()
            .id(PreisApiData.PRODUKT1_ID)
            .produktId(PreisApiData.PRODUKT1_ID)
            .amount(PreisApiData.PREIS1_DTO.getAmount())
            .currency(PreisApiData.PREIS1_DTO.getCurrency())
            .build();
    Preis PREIS2 = Preis.builder()
            .id(PreisApiData.PRODUKT2_ID)
            .produktId(PreisApiData.PRODUKT2_ID)
            .amount(PreisApiData.PREIS2_DTO.getAmount())
            .currency(PreisApiData.PREIS2_DTO.getCurrency())
            .build();
    Set<Preis> PREISE = Set.of(PREIS1, PREIS2);
}
