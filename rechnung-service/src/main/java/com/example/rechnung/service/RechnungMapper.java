package com.example.rechnung.service;

import com.example.kunde.api.KundeDto;
import com.example.produkt.api.ProduktDto;
import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Mapper(imports = UUID.class)
interface RechnungMapper {

    RechnungDto map(Rechnung rechnung);

    default Rechnung map(final BestellungDto bestellung, @Context final Supplier<KundeDto> kunde, @Context final Function<UUID, ProduktDto> produkte) {
        return map(bestellung, kunde.get(), produkte);
    }

    @Mapping(target = "rechnungId", expression = "java( UUID.randomUUID() )")
    @Mapping(target = "strasse", source = "kunde.rechnungsadresse.strasse")
    @Mapping(target = "hausnummer", source = "kunde.rechnungsadresse.hausnummer")
    @Mapping(target = "plz", source = "kunde.rechnungsadresse.plz")
    @Mapping(target = "wohnort", source = "kunde.rechnungsadresse.wohnort")
    Rechnung map(BestellungDto bestellung, KundeDto kunde, @Context Function<UUID, ProduktDto> produkte);

    default Rechnung.Posten map(final BestellungDto.Posten posten, @Context final Function<UUID, ProduktDto> produkte) {
        return map(posten, produkte.apply(posten.getProduktId()));
    }

    @Mapping(target = "produkt", source = "produkt.bezeichnung")
    Rechnung.Posten map(final BestellungDto.Posten posten, ProduktDto produkt);
}
