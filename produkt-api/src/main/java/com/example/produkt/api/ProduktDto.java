package com.example.produkt.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProduktDto {

    @NotNull
    private UUID id;

    @NotEmpty
    private String bezeichnung;

    @NotEmpty
    private String beschreibung;

    @Positive
    private BigDecimal preis;
}
