package com.example.kunde.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
class Adresse {

    @Id
    private UUID id;

    @NotEmpty
    private String strasse;

    @NotEmpty
    private String hausnummer;

    @NotEmpty
    private String plz;

    @NotEmpty
    private String wohnort;
}
