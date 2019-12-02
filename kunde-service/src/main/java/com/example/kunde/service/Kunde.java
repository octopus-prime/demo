package com.example.kunde.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
class Kunde {

    @Id
    private UUID id;

    @NotEmpty
    private String vorname;

    @NotEmpty
    private String nachname;

    @OneToOne
    @NotNull
    @Valid
    private Adresse lieferadresse;

    @OneToOne
    @Valid
    private Adresse rechnungsadresse;
}
