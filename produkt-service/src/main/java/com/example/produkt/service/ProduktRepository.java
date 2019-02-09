package com.example.produkt.service;

import com.example.produkt.api.Produkt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

//@Api("foo")
@RepositoryRestResource(collectionResourceRel = "produkte", path = "produkte")
@Cacheable("produkte")
public interface ProduktRepository extends MongoRepository<Produkt, UUID> {

    Set<Produkt> findAllByIdIn(@RequestParam("produktIds") final Set<UUID> produktIds);
}
