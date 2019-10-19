package com.example.produkt.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Document
class Produkt {

    @Id
    private UUID id;

    private String bezeichnung;
    private String beschreibung;
}
