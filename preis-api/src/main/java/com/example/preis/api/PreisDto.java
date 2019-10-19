package com.example.preis.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class PreisDto {

    private UUID id;
    private UUID produktId;
    private Number amount;
    private String currency;
}
