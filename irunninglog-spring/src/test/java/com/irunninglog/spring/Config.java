package com.irunninglog.spring;

import com.irunninglog.spring.strava.IStravaService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.irunninglog")
public class Config {

    static {
        System.setProperty("dataSource", "org.h2.Driver|jdbc:h2:mem:test;DB_CLOSE_DELAY=-1|sa");
        System.setProperty("jpa", "update|org.hibernate.dialect.H2Dialect");
        System.setProperty("jwt", "issuer|secret");
        System.setProperty("strava", "1|foo");
    }

    @Bean
    public IStravaService stravaService() {
        return Mockito.mock(IStravaService.class);
    }

}
