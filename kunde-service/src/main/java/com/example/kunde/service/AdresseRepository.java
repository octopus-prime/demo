package com.example.kunde.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AdresseRepository extends JpaRepository<Adresse, UUID> {
}
