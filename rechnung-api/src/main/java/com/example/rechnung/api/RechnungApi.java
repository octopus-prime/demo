package com.example.rechnung.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface RechnungApi {

    @PostMapping(path = "rechnungen", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Rechnung createRechnung(@Validated @RequestBody Bestellung bestellung);

    @GetMapping(path = "rechnungen/{rechnungId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Rechnung getRechnung(@PathVariable("rechnungId") UUID rechnungId);
}
