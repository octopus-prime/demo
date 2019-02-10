package com.example.rechnung.service;

import com.example.rechnung.api.BestellungDto;

import java.util.Arrays;

import static com.example.rechnung.service.ProduktData.PRODUKT1_ID;
import static com.example.rechnung.service.ProduktData.PRODUKT2_ID;

class BestellungData {

    static final BestellungDto BESTELLUNG_DTO = BestellungDto.builder()
            .kundeId(KundeData.KUNDE_ID)
            .warenkorb(Arrays.asList(
                    BestellungDto.Posten.builder()
                            .produktId(PRODUKT1_ID)
                            .anzahl(2)
                            .build(),
                    BestellungDto.Posten.builder()
                            .produktId(PRODUKT2_ID)
                            .anzahl(1)
                            .build()
            ))
            .build();

}
