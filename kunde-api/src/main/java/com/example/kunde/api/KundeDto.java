package com.example.kunde.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class KundeDto {

    @NotEmpty
    private UUID id;

    @NotEmpty
    private String vorname;

    @NotEmpty
    private String nachname;

    @NotNull
    @Valid
    private AdresseDto rechnungsadresse;

    @Valid
    private AdresseDto lieferadresse;
}
