package com.example.kundeservice.impl;

import com.example.kundeservice.api.Kunde;
import com.example.kundeservice.api.KundeApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
public class KundeController implements KundeApi {

    private static final UUID NON_EXISTING_KUNDE_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Override
    public Kunde getKunde(final UUID kundeId) {
        if (NON_EXISTING_KUNDE_ID.equals(kundeId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kunde not found");

        final Kunde.Adresse adresse = Kunde.Adresse.builder()
                .strasse("Musterstrasse")
                .hausnummer("17a")
                .plz("12345")
                .wohnort("Musterstadt")
                .build();
        return Kunde.builder()
                .id(kundeId)
                .vorname("Max")
                .nachname("Mustermann")
                .rechnungsadresse(adresse)
                .lieferadresse(adresse)
                .build();
    }
}
