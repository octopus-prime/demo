package com.example.rechnung.service;

import com.example.rechnung.api.Rechnung;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface RechnungRepository extends MongoRepository<Rechnung, UUID> {
}
