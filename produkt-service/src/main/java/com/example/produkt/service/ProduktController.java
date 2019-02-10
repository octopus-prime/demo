package com.example.produkt.service;

import com.example.produkt.api.ProduktApi;
import com.example.produkt.api.ProduktDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

@Controller
public class ProduktController implements ProduktApi {

    private final ProduktRepository produktRepository;
    private final ProduktMapper produktMapper;

    @Autowired
    public ProduktController(final ProduktRepository produktRepository, final ProduktMapper produktMapper) {
        this.produktRepository = produktRepository;
        this.produktMapper = produktMapper;
    }

    @Override
    public Set<ProduktDto> getProdukte(@RequestParam("produktIds") final Set<UUID> produktIds) {
        final Set<Produkt> produkts = produktRepository.findAllByIdIn(produktIds);
        return produktMapper.map(produkts);
    }
}
