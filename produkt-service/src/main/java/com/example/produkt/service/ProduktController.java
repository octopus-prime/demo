package com.example.produkt.service;

import com.example.produkt.api.ProduktApi;
import com.example.produkt.api.ProduktDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
class ProduktController implements ProduktApi {

    private final ProduktService produktService;

    @Autowired
    ProduktController(final ProduktService produktService) {
        this.produktService = produktService;
    }

    @Override
    public Set<ProduktDto> getProdukte(@RequestParam("produktIds") final Set<UUID> produktIds) {
        return produktService.getProdukte(produktIds);
    }
}
