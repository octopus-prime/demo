package com.example.rechnung.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bestellung {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
