package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    static {
        System.setProperty("strava", "1|foo");
    }

    @Bean
    public IFactory factory() {
        return Mockito.mock(IFactory.class);
    }

}
