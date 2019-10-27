package com.example.preis.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.UUID;

@Tag(name = "Preis")
public interface PreisApi {

    @Operation(summary = "Get kunde")
    @GetMapping(path = "preise", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Set<PreisDto> getPreise(@RequestParam("produktIds") Set<UUID> produktIds);
}
