package com.example.preis.service;

import com.example.preis.api.PreisDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
interface PreisMapper {

    Set<PreisDto> map(Set<Preis> preise);
}
