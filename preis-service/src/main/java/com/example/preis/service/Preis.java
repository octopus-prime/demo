package com.example.preis.service;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document
class Preis {

    @Id
    private UUID id;
    @Indexed
    private UUID produktId;
    private Number amount;
    private String currency;
}