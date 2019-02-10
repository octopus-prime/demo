package com.example.rechnung.api;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BestellungDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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
