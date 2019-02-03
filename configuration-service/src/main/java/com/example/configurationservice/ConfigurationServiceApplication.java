package com.example.configurationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ConfigurationServiceApplication.class, args);
    }
}
