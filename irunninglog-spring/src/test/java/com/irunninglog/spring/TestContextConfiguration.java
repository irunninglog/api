package com.irunninglog.spring;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContextConfiguration {

    static {
        System.setProperty("dataSource", "org.h2.Driver|jdbc:h2:mem:test;DB_CLOSE_DELAY=-1|sa");
        System.setProperty("jpa", "update|org.hibernate.dialect.H2Dialect");
        System.setProperty("jwt", "issuer|secret");
        System.setProperty("strava", "1|foo");
    }

}
