package com.example.produkt.service;

import com.example.produkt.api.Produkt;
import com.example.produkt.api.ProduktApi;
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
