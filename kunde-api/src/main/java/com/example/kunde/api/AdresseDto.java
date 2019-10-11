package com.example.kunde.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class AdresseDto {

    private String strasse;
    private String hausnummer;
    private String plz;
    private String wohnort;
}
