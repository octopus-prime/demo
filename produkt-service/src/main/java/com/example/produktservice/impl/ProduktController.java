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

    @Autowired
    private ProduktRepository produktRepository;

    @Override
    public Set<Produkt> getProdukte(@RequestParam("produktIds") final Set<UUID> produktIds) {
        return produktRepository.findAllByIdIn(produktIds);
//        return stream(produktRepository.findAllById(produktIds).spliterator(), false).collect(toMap(Produkt::getId, identity()));
    }

//    @Override
//    public Map<UUID, Produkt> getProdukte(@RequestParam("produktIds") final Set<UUID> produktIds) {
//        return stream(produktRepository.findAllById(produktIds).spliterator(), false).collect(toMap(Produkt::getId, identity()));
//    }

//    @Override
//    public Produkt getProdukt(final UUID produktId) {
//        return Produkt.builder()
//                .id(produktId)
//                .bezeichnung("Asus ROG Swift PG27UQ")
//                .beschreibung("68,58 cm (27 Zoll) Gaming Monitor (4K UHD, bis zu 144Hz, G-Sync, HDR, Quantom-Dot, Aura Sync, DisplayPort, HDMI) schwarz")
//                .build();
//    }
}
