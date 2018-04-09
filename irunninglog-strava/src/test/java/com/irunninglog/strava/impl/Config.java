package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.strava.*;
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
        return new StravaServiceImpl(factory(), exchange(), cache());
    }

    @Bean
    public IStravaTokenExchange exchange() {
        return Mockito.mock(IStravaTokenExchange.class);
    }

    @Bean
    public StravaSessionCache cache() {
        return new StravaSessionCache(factory());
    }

    @Bean
    @Scope("prototype")
    public IStravaAthlete athlete() {
        return new StravaAthleteImpl();
    }

    @Bean
    @Scope("prototype")
    public IRun run() {
        return new TestRun();
    }

    @Bean
    @Scope("prototype")
    public IStravaSession session() {
        return new StravaSessionImpl(factory());
    }

    @Bean
    @Scope("prototype")
    public IStravaRemoteApi api() {
        return Mockito.mock(IStravaRemoteApi.class);
    }

}
