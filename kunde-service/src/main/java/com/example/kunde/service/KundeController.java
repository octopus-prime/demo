package com.example.kunde.service;

import com.example.kunde.api.KundeApi;
import com.example.kunde.api.KundeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
class KundeController implements KundeApi {

    private final KundeService kundeService;

    @Autowired
    KundeController(final KundeService kundeService) {
        this.kundeService = kundeService;
    }

    @Override
    public KundeDto getKunde(final UUID kundeId) {
        return kundeService.getKunde(kundeId);
    }
}
