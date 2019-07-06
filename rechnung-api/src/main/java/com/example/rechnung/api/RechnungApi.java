package com.example.rechnung.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface RechnungApi {

    @PostMapping(path = "rechnungen", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    RechnungDto createRechnung(@RequestBody BestellungDto bestellung);

    @GetMapping(path = "rechnungen/{rechnungId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    RechnungDto getRechnung(@PathVariable("rechnungId") UUID rechnungId);
}
