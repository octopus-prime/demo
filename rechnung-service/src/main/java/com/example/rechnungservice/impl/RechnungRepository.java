package com.example.rechnungservice.impl;

import com.example.rechnungservice.api.Rechnung;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RechnungRepository extends MongoRepository<Rechnung, UUID> {
}
