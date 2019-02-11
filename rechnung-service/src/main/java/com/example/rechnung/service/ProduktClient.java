package com.example.rechnung.service;

import com.example.produkt.api.ProduktApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "produkt-service", decode404 = true)
interface ProduktClient extends ProduktApi {
}