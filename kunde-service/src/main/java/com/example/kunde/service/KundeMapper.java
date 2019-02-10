package com.example.kunde.service;

import com.example.kunde.api.KundeDto;
import org.mapstruct.Mapper;

@Mapper
interface KundeMapper {

    KundeDto map(Kunde kunde);
}
