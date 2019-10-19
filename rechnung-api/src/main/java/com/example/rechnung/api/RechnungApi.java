package com.example.rechnung.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Rechnung")
public interface RechnungApi {

    @Operation(summary = "Create rechnung")
    @PostMapping(path = "rechnungen", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    RechnungDto createRechnung(@RequestBody BestellungDto bestellung);

    @Operation(summary = "Get rechnung")
    @GetMapping(path = "rechnungen/{rechnungId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    RechnungDto getRechnung(@PathVariable("rechnungId") UUID rechnungId);
}
