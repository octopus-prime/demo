package com.example.produkt.service;

import com.example.produkt.api.ProduktDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface ProduktMapper {

    Set<ProduktDto> map(Set<Produkt> produkte);
}
