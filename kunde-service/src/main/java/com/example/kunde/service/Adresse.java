package com.example.kunde.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
class Adresse {

    @Id
    private UUID id;

    private String strasse;
    private String hausnummer;
    private String plz;
    private String wohnort;
}
