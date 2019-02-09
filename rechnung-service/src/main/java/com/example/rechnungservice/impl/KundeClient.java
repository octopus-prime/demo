package com.example.rechnungservice.impl;

import com.example.kunde.api.KundeApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "kunde-service", decode404 = true)
public interface KundeClient extends KundeApi {
}
