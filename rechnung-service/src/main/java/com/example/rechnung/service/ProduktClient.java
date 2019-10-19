package com.example.rechnung.service;

import com.example.produkt.api.ProduktApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "produkt-service", path = "/produkt-api", decode404 = true)
interface ProduktClient extends ProduktApi {
}
