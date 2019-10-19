package com.example.kunde.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Kunde")
public interface KundeApi {

    @Operation(summary = "Get kunde")
    @GetMapping(path = "kunden/{kundeId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    KundeDto getKunde(@PathVariable("kundeId") UUID kundeId);
}
