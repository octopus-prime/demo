package com.example.preis.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@Document
class Preis {

    @Id
    private UUID id;

    @Indexed
    private UUID produktId;

    private Number amount;
    private String currency;
}
