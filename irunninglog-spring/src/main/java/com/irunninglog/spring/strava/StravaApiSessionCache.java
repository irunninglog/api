package com.irunninglog.spring.strava;

import com.irunninglog.api.factory.IFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StravaApiSessionCache {

    private final IFactory factory;
    private final Map<String, StravaApiSession> map = new HashMap<>();

    StravaApiSessionCache(IFactory factory) {
        this.factory = factory;
    }

    StravaApiSession get(String token) {
        return map.get(token);
    }

    StravaApiSession create(String token) {
        return map.computeIfAbsent(token, s ->  {
            StravaApiSession session = factory.get(StravaApiSession.class);
            session.load(token);
            return session;
        });
    }

    public void clear() {
        map.clear();
    }

}
