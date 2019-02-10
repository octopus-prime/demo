package com.example.rechnung.service;

import com.example.kunde.api.KundeApi;
import com.example.kunde.api.KundeDto;
import com.example.preis.api.PreisApi;
import com.example.preis.api.PreisDto;
import com.example.produkt.api.ProduktApi;
import com.example.produkt.api.ProduktDto;
import com.example.rechnung.api.Bestellung;
import com.example.rechnung.api.Rechnung;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
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
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Service
class RechnungService {

    private final KundeApi kundeClient;
    private final ProduktApi produktClient;
    private final PreisApi preisClient;
    private final RechnungRepository rechnungRepository;
    private final ExecutorService executorService;

    @Autowired
    RechnungService(final KundeApi kundeClient, final ProduktApi produktClient, final PreisApi preisClient,
                    final RechnungRepository rechnungRepository, final BeanFactory beanFactory) {
        this.kundeClient = kundeClient;
        this.produktClient = produktClient;
        this.preisClient = preisClient;
        this.rechnungRepository = rechnungRepository;
        executorService = new TraceableExecutorService(beanFactory, Executors.newCachedThreadPool());
    }

    Rechnung getRechnung(final UUID rechnungId) {
        return rechnungRepository.findById(rechnungId).orElseThrow(NotFound.RECHNUNG);
    }

    Rechnung createRechnung(final Bestellung bestellung) {
        final var rechnung = mapRechnung(bestellung);
        return rechnungRepository.save(rechnung);
    }

    private Rechnung mapRechnung(final Bestellung bestellung) {
        final var produktIds = bestellung.getWarenkorb().stream().map(Bestellung.Posten::getProduktId).collect(toSet());
        final var getKunde = CompletableFuture.supplyAsync(() -> kundeClient.getKunde(bestellung.getKundeId()), executorService);
        final var getProdukte = CompletableFuture.supplyAsync(() -> produktClient.getProdukte(produktIds), executorService);
        final var getPreise = CompletableFuture.supplyAsync(() -> preisClient.getPreise(produktIds), executorService);
        return CompletableFuture.allOf(getKunde, getProdukte, getPreise).thenApply(
                unused -> new Mapper(getKunde.join(), getProdukte.join(), getPreise.join()).map(bestellung)
        ).join();
    }

    private static class Mapper {

        private final Optional<KundeDto> kunde;
        private final Map<UUID, ProduktDto> produkte;
        private final Map<UUID, PreisDto> preise;

        Mapper(final KundeDto kunde, final Set<ProduktDto> produkte, final Set<PreisDto> preise) {
            this.kunde = Optional.ofNullable(kunde);
            this.produkte = produkte.stream().collect(toMap(ProduktDto::getId, identity()));
            this.preise = preise.stream().collect(toMap(PreisDto::getProduktId, identity()));
        }

        Rechnung map(final Bestellung bestellung) {
            final var kunde = getKunde();
            final var adresse = kunde.getRechnungsadresse();
            final var warenkorb = bestellung.getWarenkorb().stream().map(this::map).collect(toList());
            return Rechnung.builder().rechnungId(UUID.randomUUID())
                    .vorname(kunde.getVorname())
                    .nachname(kunde.getNachname())
                    .strasse(adresse.getStrasse())
                    .hausnummer(adresse.getHausnummer())
                    .plz(adresse.getPlz())
                    .wohnort(adresse.getWohnort())
                    .warenkorb(warenkorb)
                    .build();
        }

        private Rechnung.Posten map(final Bestellung.Posten posten) {
            final var produktId = posten.getProduktId();
            return Rechnung.Posten.builder()
                    .anzahl(posten.getAnzahl())
                    .preis(getPreis(produktId).getAmount())
                    .produkt(getProdukt(produktId).getBezeichnung())
                    .build();

        }

        private KundeDto getKunde() {
            return kunde.orElseThrow(NotFound.KUNDE);
        }

        private ProduktDto getProdukt(final UUID produktId) {
            return Optional.ofNullable(produkte.get(produktId)).orElseThrow(NotFound.PRODUKT);
        }

        private PreisDto getPreis(final UUID produktId) {
            return Optional.ofNullable(preise.get(produktId)).orElseThrow(NotFound.PREIS);
        }
    }

    @RequiredArgsConstructor
    private enum NotFound implements Supplier<ResponseStatusException> {

        KUNDE("Kunde"),
        PRODUKT("Produkt"),
        PREIS("Preis"),
        RECHNUNG("Rechnung");

        private final String type;

        @Override
        public ResponseStatusException get() {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, type + " not found");
        }
    }
}
