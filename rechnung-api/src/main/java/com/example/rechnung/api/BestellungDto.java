package com.example.rechnung.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class BestellungDto {

    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class Posten {

        @NotNull
        private UUID produktId;

        @NotNull
        private Integer anzahl;
    }

    @NotNull
    private UUID kundeId;

    @NotNull
    private List<Posten> warenkorb;
}
