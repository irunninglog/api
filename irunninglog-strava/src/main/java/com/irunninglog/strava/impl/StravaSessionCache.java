package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.strava.IStravaSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
final class StravaSessionCache {

    private final IFactory factory;
    private final Map<String, IStravaSession> map = new HashMap<>();

    @Autowired
    StravaSessionCache(IFactory factory) {
        this.factory = factory;
    }

    IStravaSession create(String token) {
        return map.computeIfAbsent(token, s ->  {
            IStravaSession session = factory.get(IStravaSession.class);
            session.load(token);
            return session;
        });
    }

    IStravaSession get(String token) {
        return map.get(token);
    }

}
