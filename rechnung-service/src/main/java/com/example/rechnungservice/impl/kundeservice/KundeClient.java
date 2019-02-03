package com.example.rechnungservice.impl.kundeservice;

import com.example.rechnungservice.impl.kundeservice.api.KundeApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "kunde-service", decode404 = true)
public interface KundeClient extends KundeApi {
}
