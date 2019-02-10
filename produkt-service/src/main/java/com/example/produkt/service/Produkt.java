package com.example.produkt.service;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document
class Produkt {

    @Id
    private UUID id;

    private String bezeichnung;
    private String beschreibung;
}
