package com.irunninglog.spring;

import com.irunninglog.spring.strava.StravaMockRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new StravaMockRestTemplate();
    }

}
