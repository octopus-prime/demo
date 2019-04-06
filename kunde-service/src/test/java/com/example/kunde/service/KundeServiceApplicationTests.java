package com.example.kunde.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.cloud.config.enabled = false", "eureka.client.enabled = false"})
public class KundeServiceApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        then(context).isNotNull();
    }
}
