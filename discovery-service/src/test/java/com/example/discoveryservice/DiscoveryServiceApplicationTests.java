package com.example.discoveryservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.cloud.config.enabled = false"})
public class DiscoveryServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

}
