package com.example.preisservice.impl;

import com.example.preisservice.api.Preis;
import com.example.preisservice.api.PreisApi;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Controller
public class PreisController implements PreisApi {

    @Override
    public Preis getPreis(final UUID produktId) {
        return Preis.builder()
                .amount(2313.39)
                .currency("€")
                .build();
    }

    @Override
    public Map<UUID, Preis> getPreise(final Set<UUID> produktIds) {
        return produktIds.stream().collect(toMap(identity(), this::getPreis));
    }
}
