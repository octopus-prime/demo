package com.example.kunde.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@NoArgsConstructor
public class AdresseDto {

    @NotEmpty
    private String strasse;

    @NotEmpty
    private String hausnummer;

    @NotEmpty
    private String plz;

    @NotEmpty
    private String wohnort;
}
