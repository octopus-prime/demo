package com.example.rechnungservice.impl;

import com.example.rechnungservice.api.Bestellung;

import java.util.Arrays;

import static com.example.rechnungservice.impl.kundeservice.KundeData.KUNDE_ID;
import static com.example.rechnungservice.impl.produktservice.ProduktData.PRODUKT1_ID;
import static com.example.rechnungservice.impl.produktservice.ProduktData.PRODUKT2_ID;

public class BestellungData {

    public static final Bestellung BESTELLUNG = Bestellung.builder()
            .kundeId(KUNDE_ID)
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
