package com.example.rechnung.service;

import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungApi;
import com.example.rechnung.api.RechnungDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
class RechnungController implements RechnungApi {

    private final RechnungService rechnungService;

    @Autowired
    RechnungController(final RechnungService rechnungService) {
        this.rechnungService = rechnungService;
    }

    @Override
    public RechnungDto createRechnung(@Validated @RequestBody final BestellungDto bestellung) {
        return rechnungService.createRechnung(bestellung);
    }

    @Override
    public RechnungDto getRechnung(@PathVariable("rechnungId") final UUID rechnungId) {
        return rechnungService.getRechnung(rechnungId);
    }
}
