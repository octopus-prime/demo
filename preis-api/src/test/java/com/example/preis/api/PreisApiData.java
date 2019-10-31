package com.example.preis.api;

import java.util.Set;
import java.util.UUID;

public interface PreisApiData {
    UUID PRODUKT1_ID = UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811");
    PreisDto PREIS1_DTO = PreisDto.builder()
            .id(PRODUKT1_ID)
            .produktId(PRODUKT1_ID)
            .amount(2313.39)
            .currency("€")
            .build();
    UUID PRODUKT2_ID = UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f");
    PreisDto PREIS2_DTO = PreisDto.builder()
            .id(PRODUKT2_ID)
            .produktId(PRODUKT2_ID)
            .amount(1199.99)
            .currency("€")
            .build();
    Set<UUID> PRODUKT_IDS = Set.of(PRODUKT1_ID, PRODUKT2_ID);
    Set<PreisDto> PREIS_DTOS = Set.of(PREIS1_DTO, PREIS2_DTO);
}
