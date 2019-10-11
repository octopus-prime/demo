package com.example.kunde.service;

import com.example.kunde.api.KundeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
class KundeService {

    private final KundeRepository kundeRepository;
    private final KundeMapper kundeMapper;

    @Autowired
    KundeService(final KundeRepository kundeRepository, final KundeMapper kundeMapper) {
        this.kundeRepository = kundeRepository;
        this.kundeMapper = kundeMapper;
    }

    KundeDto getKunde(final UUID kundeId) {
        final var kunde = kundeRepository
                .findById(kundeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kunde not found"));
        return kundeMapper.map(kunde);
    }
}
