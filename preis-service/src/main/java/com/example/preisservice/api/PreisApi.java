package com.example.preisservice.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface PreisApi {

    @GetMapping(path = "preise/{produktId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Preis getPreis(@PathVariable("produktId") UUID produktId);

    @GetMapping(path = "preise", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Map<UUID, Preis> getPreise(@RequestParam("produktIds") Set<UUID> produktIds);
}
