package com.example.rechnung.service;

import com.example.preis.api.PreisApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "preis-service", path = "/preis-api")
interface PreisClient extends PreisApi {
}
