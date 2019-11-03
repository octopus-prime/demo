package com.example.common;

import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class RestAssuredExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(final ExtensionContext context) {
        final ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        final Environment environment = applicationContext.getEnvironment();
        RestAssured.port = Integer.parseInt(environment.getProperty("local.server.port"));
        RestAssured.basePath = environment.getProperty("server.servlet.context-path");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
