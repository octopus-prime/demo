package com.example.produkt.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

interface ProduktRepository extends MongoRepository<Produkt, UUID> {

    Set<Produkt> findAllByIdIn(@RequestParam("produktIds") final Set<UUID> produktIds);
}
