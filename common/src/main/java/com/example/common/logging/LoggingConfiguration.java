package com.example.common.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingConfiguration {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        return new LoggingFilter();
    }

}
