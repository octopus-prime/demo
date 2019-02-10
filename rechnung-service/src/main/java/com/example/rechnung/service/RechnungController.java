package com.example.rechnung.service;

import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungApi;
import com.example.rechnung.api.RechnungDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
@Api("Rechnungen")
class RechnungController implements RechnungApi {

    private final RechnungService rechnungService;

    @Autowired
    RechnungController(final RechnungService rechnungService) {
        this.rechnungService = rechnungService;
    }

    @Override
    @ApiOperation("Creates a Rechnung")
    public RechnungDto createRechnung(@Validated @RequestBody final BestellungDto bestellung) {
        return rechnungService.createRechnung(bestellung);
    }

    @Override
    @ApiOperation("Gets a Rechnung")
    public RechnungDto getRechnung(@PathVariable("rechnungId") final UUID rechnungId) {
        return rechnungService.getRechnung(rechnungId);
    }
}
