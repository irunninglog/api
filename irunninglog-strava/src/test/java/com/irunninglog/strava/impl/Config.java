package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.strava.IStravaApi;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {

    @Bean
    public IFactory factory() {
        return Mockito.mock(IFactory.class);
    }

    @Bean
    public IStravaService service() {
        return new StravaServiceImpl(factory(), api());
    }
    @Bean
    public IStravaApi api() {
        return Mockito.mock(IStravaApi.class);
    }

    @Bean
    @Scope("prototype")
    public IStravaAthlete athlete() {
        return new StravaAthleteImpl();
    }

    @Bean
    @Scope("prototype")
    public IStravaRun run() {
        return new StravaRunImpl();
    }

}
