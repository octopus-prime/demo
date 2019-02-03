package com.example.produktservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
//@Import(SpringDataRestConfiguration.class)
@EnableCaching
public class ProduktServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ProduktServiceApplication.class, args);
    }
}
