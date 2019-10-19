package com.example.rechnung.service;

import com.example.kunde.api.KundeApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "kunde-service", path = "/kunde-api", decode404 = true)
interface KundeClient extends KundeApi {
}
