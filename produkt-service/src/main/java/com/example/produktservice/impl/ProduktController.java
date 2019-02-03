package com.example.produktservice.impl;

import com.example.produktservice.api.Produkt;
import com.example.produktservice.api.ProduktApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

@Controller
public class ProduktController implements ProduktApi {

    private final ProduktRepository produktRepository;

    @Autowired
    public ProduktController(final ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }

    @Override
    public Set<Produkt> getProdukte(@RequestParam("produktIds") final Set<UUID> produktIds) {
        return produktRepository.findAllByIdIn(produktIds);
    }
}
