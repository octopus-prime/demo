package com.example.rechnung.service;

import com.example.rechnung.api.Bestellung;
import com.example.rechnung.service.kundeservice.KundeData;

import java.util.Arrays;

import static com.example.rechnung.service.produktservice.ProduktData.PRODUKT1_ID;
import static com.example.rechnung.service.produktservice.ProduktData.PRODUKT2_ID;

public class BestellungData {

    public static final Bestellung BESTELLUNG = Bestellung.builder()
            .kundeId(KundeData.KUNDE_ID)
            .warenkorb(Arrays.asList(
                    Bestellung.Posten.builder()
                            .produktId(PRODUKT1_ID)
                            .anzahl(2)
                            .build(),
                    Bestellung.Posten.builder()
                            .produktId(PRODUKT2_ID)
                            .anzahl(1)
                            .build()
            ))
            .build();

}
