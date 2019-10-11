package com.example.kunde.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
class Kunde {

    @Id
    private UUID id;

    private String vorname;
    private String nachname;

    @OneToOne
    private Adresse lieferadresse;

    @OneToOne
    private Adresse rechnungsadresse;
}
