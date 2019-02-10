package com.example.produkt.api;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProduktDto {

    private UUID id;
    private String bezeichnung;
    private String beschreibung;
}
