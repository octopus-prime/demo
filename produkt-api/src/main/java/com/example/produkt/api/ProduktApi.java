package com.example.produkt.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;
import java.util.UUID;

public interface ProduktApi {

    @GetMapping(path = "produkte", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Set<ProduktDto> getProdukte(@RequestParam("produktIds") Set<UUID> produktIds);
}