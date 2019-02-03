package com.example.kundeservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class LoggingConfiguration {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        return new LoggingFilter();
    }

    public class LoggingFilter extends CommonsRequestLoggingFilter {

        LoggingFilter() {
            setIncludeQueryString(true);
            setIncludeHeaders(true);
            setIncludePayload(true);
            setMaxPayloadLength(10000);
            setAfterMessagePrefix("Request ");
        }

        @Override
        protected void beforeRequest(final HttpServletRequest request, final String message) {
            // Do not log
        }
    }
}
