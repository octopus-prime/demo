package com.example.preisservice.impl;

import com.example.preisservice.api.Preis;
import com.example.preisservice.api.PreisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;
import java.util.UUID;

@Controller
public class PreisController implements PreisApi {

    private final PreisRepository preisRepository;

    @Autowired
    public PreisController(final PreisRepository preisRepository) {
        this.preisRepository = preisRepository;
    }

    @Override
    public Set<Preis> getPreise(final Set<UUID> produktIds) {
        return preisRepository.findAllByProduktIdIn(produktIds);
    }
}
