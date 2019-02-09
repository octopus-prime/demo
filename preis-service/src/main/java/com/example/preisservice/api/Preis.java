package com.example.preisservice.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@Document
public class Preis {

    @Id
    private UUID id;
    @Indexed
    private UUID produktId;
    private Number amount;
    private String currency;
}
