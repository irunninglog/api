package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Configuration
public class StravaConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Autowired
    public IStravaService service(IFactory factory) {
        return new StravaServiceImpl(environment, factory);
    }

    @Bean
    @Scope("prototype")
    public IStravaAthlete athlete() {
        return new StravaAthleteImpl();
    }

}
