package com.example.rechnung.service;

import com.example.kunde.api.KundeApi;
import com.example.kunde.api.KundeDto;
import com.example.produkt.api.ProduktApi;
import com.example.produkt.api.ProduktDto;
import com.example.rechnung.api.BestellungDto;
import com.example.rechnung.api.RechnungDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Service
class RechnungService {

    private final KundeApi kundeApi;
    private final ProduktApi produktApi;
    private final RechnungRepository rechnungRepository;
    private final RechnungMapper rechnungMapper;
    private final ExecutorService executorService;

    @Autowired
    RechnungService(final KundeApi kundeApi, final ProduktApi produktApi,
                    final RechnungRepository rechnungRepository, final RechnungMapper rechnungMapper,
                    final TraceableExecutorService executorService) {
        this.kundeApi = kundeApi;
        this.produktApi = produktApi;
        this.rechnungRepository = rechnungRepository;
        this.rechnungMapper = rechnungMapper;
        this.executorService = executorService;
    }

    RechnungDto getRechnung(final UUID rechnungId) {
        final var rechnung = rechnungRepository.findById(rechnungId).orElseThrow(NotFound.RECHNUNG);
        return rechnungMapper.map(rechnung);
    }

    RechnungDto createRechnung(final BestellungDto bestellung) {
        final var rechnung = mapRechnung(bestellung);
        rechnungRepository.save(rechnung);
        return rechnungMapper.map(rechnung);
    }

    private Rechnung mapRechnung(final BestellungDto bestellung) {
        final var produktIds = bestellung.getWarenkorb().stream().map(BestellungDto.Posten::getProduktId).collect(toSet());
        final var getKunde = CompletableFuture.supplyAsync(() -> kundeApi.getKunde(bestellung.getKundeId()), executorService);
        final var getProdukte = CompletableFuture.supplyAsync(() -> produktApi.getProdukte(produktIds), executorService);
        return CompletableFuture.allOf(getKunde, getProdukte).thenApply(
                unused -> new Mapper(getKunde.join(), getProdukte.join()).map(bestellung)
        ).join();
    }

    private static class Mapper {

        private final KundeDto kunde;
        private final Map<UUID, ProduktDto> produkte;

        Mapper(final KundeDto kunde, final Set<ProduktDto> produkte) {
            this.kunde = kunde;
            this.produkte = produkte.stream().collect(toMap(ProduktDto::getId, identity()));
        }

        Rechnung map(final BestellungDto bestellung) {
            final var kunde = getKunde();
            final var adresse = kunde.getRechnungsadresse();
            final var warenkorb = bestellung.getWarenkorb().stream().map(this::map).collect(toList());
            return Rechnung.builder()
                    .rechnungId(UUID.randomUUID())
                    .vorname(kunde.getVorname())
                    .nachname(kunde.getNachname())
                    .strasse(adresse.getStrasse())
                    .hausnummer(adresse.getHausnummer())
                    .plz(adresse.getPlz())
                    .wohnort(adresse.getWohnort())
                    .warenkorb(warenkorb)
                    .build();
        }

        private Rechnung.Posten map(final BestellungDto.Posten posten) {
            final var produkt = getProdukt(posten.getProduktId());
            return Rechnung.Posten.builder()
                    .anzahl(posten.getAnzahl())
                    .preis(produkt.getPreis())
                    .produkt(produkt.getBezeichnung())
                    .build();

        }

        private KundeDto getKunde() {
            return Optional.ofNullable(kunde).orElseThrow(NotFound.KUNDE);
        }

        private ProduktDto getProdukt(final UUID produktId) {
            return Optional.ofNullable(produkte.get(produktId)).orElseThrow(NotFound.PRODUKT);
        }
    }

    @RequiredArgsConstructor
    private enum NotFound implements Supplier<ResponseStatusException> {

        KUNDE("Kunde"),
        PRODUKT("Produkt"),
        RECHNUNG("Rechnung");

        private final String type;

        @Override
        public ResponseStatusException get() {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, type + " not found");
        }
    }
}
