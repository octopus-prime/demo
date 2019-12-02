package com.example.produkt.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Document
class Produkt {

    @Id
    private UUID id;

    @NotEmpty
    private String bezeichnung;

    @NotEmpty
    private String beschreibung;

    @Positive
    private BigDecimal preis;
}
