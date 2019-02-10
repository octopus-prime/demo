package com.example.preis.service;

import com.example.preis.api.PreisApi;
import com.example.preis.api.PreisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;
import java.util.UUID;

@Controller
class PreisController implements PreisApi {

    private final PreisRepository preisRepository;
    private final PreisMapper preisMapper;

    @Autowired
    public PreisController(final PreisRepository preisRepository, final PreisMapper preisMapper) {
        this.preisRepository = preisRepository;
        this.preisMapper = preisMapper;
    }

    @Override
    public Set<PreisDto> getPreise(final Set<UUID> produktIds) {
        final var preise = preisRepository.findAllByProduktIdIn(produktIds);
        return preisMapper.map(preise);
    }
}
