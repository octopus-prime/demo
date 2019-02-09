package com.example.common;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

class LoggingFilter extends CommonsRequestLoggingFilter {

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
