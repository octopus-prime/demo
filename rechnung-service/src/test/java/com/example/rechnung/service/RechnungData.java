package com.example.rechnung.service;

import com.example.rechnung.api.Rechnung;

import java.util.UUID;

public class RechnungData {

    public static final UUID RECHNUNG_ID = UUID.fromString("2d9f379e-4b5f-4439-ad67-ac9e9cc722d3");
    public static final Rechnung RECHNUNG = Rechnung.builder()
            .rechnungId(RECHNUNG_ID)
            .vorname(KundeData.KUNDE.getVorname())
            .nachname(KundeData.KUNDE.getNachname())
            .build();
}
