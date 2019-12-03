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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

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
        final var getKunde = getKunde(bestellung);
        final var getProdukte = getProdukte(bestellung);
        return CompletableFuture.allOf(getKunde, getProdukte)
                .thenApply(unused -> mapRechnung(bestellung, getKunde.join(), getProdukte.join()))
                .join();
    }

    private Rechnung mapRechnung(final BestellungDto bestellung, final KundeDto kunde, final Map<UUID, ProduktDto> produkte) {
        return rechnungMapper.map(
                bestellung,
                () -> Optional.ofNullable(kunde).orElseThrow(NotFound.KUNDE),
                id -> Optional.ofNullable(produkte.get(id)).orElseThrow(NotFound.PRODUKT)
        );
    }

    private CompletableFuture<KundeDto> getKunde(final BestellungDto bestellung) {
        return CompletableFuture.supplyAsync(bestellung::getKundeId, executorService)
                .thenApply(kundeApi::getKunde);
    }

    private CompletableFuture<Map<UUID, ProduktDto>> getProdukte(final BestellungDto bestellung) {
        return CompletableFuture.supplyAsync(bestellung::getWarenkorb, executorService)
                .thenApply(RechnungService::getProduktIds)
                .thenApply(produktApi::getProdukte)
                .thenApply(RechnungService::getProduktMap);
    }

    private static Set<UUID> getProduktIds(final List<BestellungDto.Posten> warenkorb) {
        return warenkorb.stream()
                .map(BestellungDto.Posten::getProduktId)
                .collect(toSet());
    }

    private static Map<UUID, ProduktDto> getProduktMap(final Set<ProduktDto> produkte) {
        return produkte.stream()
                .collect(toMap(ProduktDto::getId, identity()));
    }

    @RequiredArgsConstructor
    enum NotFound implements Supplier<ResponseStatusException> {

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
