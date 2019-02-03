package com.example.rechnungservice.api;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class Bestellung {

    @Data
    @Builder
    public static class Posten {

        @NotNull
        private final UUID produktId;

        @NotNull
        private final Integer anzahl;
    }

    @NotNull
    private final UUID kundeId;

    @NotNull
    private final List<Posten> warenkorb;
}
