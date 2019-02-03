package com.example.rechnungservice.impl.produktservice;

import com.example.rechnungservice.impl.produktservice.api.ProduktApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "produkt-service", decode404 = true)
public interface ProduktClient extends ProduktApi {
}
