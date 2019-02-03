package com.example.rechnungservice.impl.preisservice.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Preis {

    private Number amount;
    private String currency;
}
