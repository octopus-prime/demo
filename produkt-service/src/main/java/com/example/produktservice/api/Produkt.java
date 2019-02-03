package com.example.produktservice.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@Document
//@NoArgsConstructor
//@AllArgsConstructor
public class Produkt {

    @Id
    private UUID id;

    private String bezeichnung;
    private String beschreibung;
}
