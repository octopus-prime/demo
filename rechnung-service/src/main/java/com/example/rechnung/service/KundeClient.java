package com.example.rechnung.service;

import com.example.kunde.api.KundeApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "kunde-service", path = "/kunde-api", decode404 = true)
//@Async
interface KundeClient extends KundeApi {
}
