package com.example.preis.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.BDDAssertions.then;

//@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(properties = {"spring.cloud.config.enabled = false", "eureka.client.enabled = false"})
public class PreisServiceApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        then(context).isNotNull();
    }
}
