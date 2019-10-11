package com.example.kunde.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class KundeDto {

    private UUID id;
    private String vorname;
    private String nachname;
    private AdresseDto lieferadresse;
    private AdresseDto rechnungsadresse;
}
