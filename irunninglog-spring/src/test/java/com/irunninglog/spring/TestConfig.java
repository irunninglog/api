package com.irunninglog.spring;

import com.irunninglog.strava.IStravaService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public IStravaService stravaService() {
        return Mockito.mock(IStravaService.class);
    }

}
