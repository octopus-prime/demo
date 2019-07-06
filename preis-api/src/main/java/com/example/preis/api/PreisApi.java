package com.example.preis.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;
import java.util.UUID;

public interface PreisApi {

    @GetMapping(path = "preise", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    Set<PreisDto> getPreise(@RequestParam("produktIds") Set<UUID> produktIds);
}
