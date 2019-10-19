package com.example.preis.service;

import com.example.preis.api.PreisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
class PreisService {

    private final PreisRepository preisRepository;
    private final PreisMapper preisMapper;

    @Autowired
    PreisService(final PreisRepository preisRepository, final PreisMapper preisMapper) {
        this.preisRepository = preisRepository;
        this.preisMapper = preisMapper;
    }

    Set<PreisDto> getPreise(final Set<UUID> produktIds) {
        final var preise = preisRepository.findAllByProduktIdIn(produktIds);
        return preisMapper.map(preise);
    }
}
