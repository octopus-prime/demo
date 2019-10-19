package com.example.preis.service;

import com.example.preis.api.PreisApi;
import com.example.preis.api.PreisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
class PreisController implements PreisApi {

    private final PreisService preisService;

    @Autowired
    PreisController(final PreisService preisService) {
        this.preisService = preisService;
    }

    @Override
    public Set<PreisDto> getPreise(final Set<UUID> produktIds) {
        return preisService.getPreise(produktIds);
    }
}
