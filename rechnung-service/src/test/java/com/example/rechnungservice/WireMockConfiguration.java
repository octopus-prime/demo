package com.example.rechnungservice;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WireMockConfiguration {
    @Bean
    public WireMockConfigurationCustomizer optionsCustomizer() {
        return options -> options.extensions(new ResponseTemplateTransformer(false));
    }
}
