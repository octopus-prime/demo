package com.example.kunde.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.cloud.config.enabled = false", "eureka.client.enabled = false"})
public class KundeServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

}
