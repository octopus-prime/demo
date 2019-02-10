package com.example.preis.api;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PreisDto {

    private UUID id;
    private UUID produktId;
    private Number amount;
    private String currency;
}
