package com.example.kunde.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface KundeRepository extends JpaRepository<Kunde, UUID> {
}
