package com.example.rechnungservice.impl.preisservice;

import com.example.rechnungservice.impl.preisservice.api.PreisApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "preis-service", decode404 = true)
public interface PreisClient extends PreisApi {
}
