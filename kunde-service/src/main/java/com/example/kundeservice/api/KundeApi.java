package com.example.kundeservice.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

public interface KundeApi {

    @GetMapping(path = "kunden/{kundeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Kunde getKunde(@PathVariable("kundeId") UUID kundeId);
}
