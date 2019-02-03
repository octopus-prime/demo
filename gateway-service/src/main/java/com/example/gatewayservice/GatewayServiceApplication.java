package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableZuulProxy
@EnableDiscoveryClient
public class GatewayServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
