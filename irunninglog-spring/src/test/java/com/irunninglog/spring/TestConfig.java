package com.irunninglog.spring;

import com.irunninglog.strava.IStravaService;
import com.irunninglog.strava.IStravaShoe;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Configuration
public class TestConfig {

    @Bean
    public IStravaService stravaService() {
        return Mockito.mock(IStravaService.class);
    }

    @Bean
    @Scope("prototype")
    public IStravaShoe stravaShoe() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        @SuppressWarnings("unchecked")
        Class<IStravaShoe> clazz = (Class<IStravaShoe>) Class.forName("com.irunninglog.strava.impl.StravaShoeImpl");
        Constructor<IStravaShoe> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(Boolean.TRUE);
        return constructor.newInstance();
    }

}
