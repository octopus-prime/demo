package com.example.preis.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PreisRepository extends MongoRepository<Preis, UUID> {

    Set<Preis> findAllByProduktIdIn(Set<UUID> produktIds);
}
