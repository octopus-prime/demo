package com.example.produkt.service;

import com.example.produkt.api.ProduktDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
class ProduktService {

    private final ProduktRepository produktRepository;
    private final ProduktMapper produktMapper;

    @Autowired
    ProduktService(final ProduktRepository produktRepository, final ProduktMapper produktMapper) {
        this.produktRepository = produktRepository;
        this.produktMapper = produktMapper;
    }

    Set<ProduktDto> getProdukte(final Set<UUID> produktIds) {
        final var produkts = produktRepository.findAllByIdIn(produktIds);
        return produktMapper.map(produkts);
    }
}
