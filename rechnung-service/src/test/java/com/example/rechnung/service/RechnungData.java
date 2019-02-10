package com.example.rechnung.service;

import com.example.rechnung.api.RechnungDto;

import java.util.UUID;

class RechnungData {

    static final UUID RECHNUNG_ID = UUID.fromString("2d9f379e-4b5f-4439-ad67-ac9e9cc722d3");
    static final Rechnung RECHNUNG = Rechnung.builder()
            .rechnungId(RECHNUNG_ID)
            .vorname(KundeData.KUNDE_DTO.getVorname())
            .nachname(KundeData.KUNDE_DTO.getNachname())
            .build();
    static final RechnungDto RECHNUNG_DTO = RechnungDto.builder()
            .rechnungId(RECHNUNG_ID)
            .vorname(KundeData.KUNDE_DTO.getVorname())
            .nachname(KundeData.KUNDE_DTO.getNachname())
            .build();
}
