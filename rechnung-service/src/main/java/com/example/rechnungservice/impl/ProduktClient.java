package com.example.rechnungservice.impl;

import com.example.produkt.api.ProduktApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "produkt-service", decode404 = true)
public interface ProduktClient extends ProduktApi {
}
