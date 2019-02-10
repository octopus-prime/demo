package com.example.kunde.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Adresse {

    @Id
    private UUID id;

    private String strasse;
    private String hausnummer;
    private String plz;
    private String wohnort;
}
