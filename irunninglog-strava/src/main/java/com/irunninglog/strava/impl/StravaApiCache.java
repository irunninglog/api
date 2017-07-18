package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.strava.IStravaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
final class StravaApiCache {

    private final IFactory factory;
    private final Map<String, IStravaApi> map = new HashMap<>();

    @Autowired
    StravaApiCache(IFactory factory) {
        this.factory = factory;
    }

    IStravaApi create(String token) {
        IStravaApi api = map.get(token);

        if (api == null) {
            api = factory.get(IStravaApi.class);
            map.put(token, api);
        }

        return api;
    }

    IStravaApi get(String token) {
        return map.get(token);
    }

}
