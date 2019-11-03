package com.example.common;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.*;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class RestAssuredExtension implements ParameterResolver, BeforeAllCallback {

    private String port;
    private String path;

    @Override
    public void beforeAll(final ExtensionContext context) {
        final ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        final Environment environment = applicationContext.getEnvironment();
        port = environment.getProperty("local.server.port");
        path = environment.getProperty("server.servlet.context-path");
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(RequestSpecification.class);
    }

    @Override
    public RequestSpecification resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setPort(Integer.parseInt(port))
                .setBasePath(path)
                .build();
    }
}
