package com.example.rechnung.service;

import com.example.preis.api.PreisApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "preis-service", decode404 = true)
public interface PreisClient extends PreisApi {
}
