package com.example.rechnung.service;

import com.example.rechnung.api.RechnungDto;
import org.mapstruct.Mapper;

@Mapper
interface RechnungMapper {

    RechnungDto map(Rechnung rechnung);
}
