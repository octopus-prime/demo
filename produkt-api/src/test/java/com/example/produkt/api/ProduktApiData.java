package com.example.produkt.api;

import java.util.Set;
import java.util.UUID;

public interface ProduktApiData {

    UUID PRODUKT1_ID = UUID.fromString("9e654cc3-acfe-462d-97c5-b1dcf6688811");
    ProduktDto PRODUKT1_DTO = ProduktDto.builder()
            .id(PRODUKT1_ID)
            .bezeichnung("Asus ROG Swift PG27UQ")
            .beschreibung("68,58 cm (27 Zoll) Gaming Monitor (4K UHD, bis zu 144Hz, G-Sync, HDR, Quantom-Dot, Aura Sync, DisplayPort, HDMI) schwarz")
            .build();

    UUID PRODUKT2_ID = UUID.fromString("65cf5cd6-b75c-4745-90fb-405844ed546f");
    ProduktDto PRODUKT2_DTO = ProduktDto.builder()
            .id(PRODUKT2_ID)
            .bezeichnung("Palit GeForce RTX 2080 Ti GamingPro OC, Grafikkarte")
            .beschreibung("High-End-Grafikkarte mit der GeForce RTX 2080 Ti GPU von NVIDIA")
            .build();
    Set<UUID> PRODUKT_IDS = Set.of(PRODUKT1_ID, PRODUKT2_ID);
    Set<ProduktDto> PRODUKT_DTOS = Set.of(PRODUKT1_DTO, PRODUKT2_DTO);
}
