package com.irunninglog.spring.strava;

import com.irunninglog.api.factory.IFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class StravaSessionCache {

    private final IFactory factory;
    private final Map<String, StravaSession> map = new HashMap<>();

    StravaSessionCache(IFactory factory) {
        this.factory = factory;
    }

    StravaSession get(String token) {
        return map.get(token);
    }

    StravaSession create(String token) {
        return map.computeIfAbsent(token, s ->  {
            StravaSession session = factory.get(StravaSession.class);
            session.load(token);
            return session;
        });
    }

    public void clear() {
        map.clear();
    }

}
