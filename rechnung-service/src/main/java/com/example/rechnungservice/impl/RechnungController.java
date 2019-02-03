package com.example.rechnungservice.impl;

import com.example.rechnungservice.api.Bestellung;
import com.example.rechnungservice.api.Rechnung;
import com.example.rechnungservice.api.RechnungApi;
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
public class RechnungController implements RechnungApi {

    private final RechnungService rechnungService;

    @Autowired
    public RechnungController(final RechnungService rechnungService) {
        this.rechnungService = rechnungService;
    }

    @Override
    @ApiOperation("Creates a Rechnung")
    public Rechnung createRechnung(@Validated @RequestBody final Bestellung bestellung) {
        return rechnungService.createRechnung(bestellung);
    }

    @Override
    @ApiOperation("Gets a Rechnung")
    public Rechnung getRechnung(@PathVariable("rechnungId") final UUID rechnungId) {
        return rechnungService.getRechnung(rechnungId);
    }
}
