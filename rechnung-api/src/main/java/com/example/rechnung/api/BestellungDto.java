package com.example.rechnung.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

        @Positive
        private Integer anzahl;
    }

    @NotNull
    private UUID kundeId;

    @NotNull
    @Valid
    private List<Posten> warenkorb;
}
