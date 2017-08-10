package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaRemoteApi;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.rest.API;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class StravaRemoteApiImpl implements IStravaRemoteApi {

    private API api;

    @Override
    public StravaActivity[] listAuthenticatedAthleteActivities(int page, int max) {
        return api.listAuthenticatedAthleteActivities(null, null, page, max);
    }

    @Override
    public StravaGear getGear(String id) {
        return api.getGear(id);
    }

    @Override
    public StravaAthlete getAuthenticatedAthlete() {
        return api.getAuthenticatedAthlete();
    }

    @Override
    public StravaStatistics statistics(Integer id) {
        return api.statistics(id);
    }

    @Override
    public void setToken(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);
        api = new API(apiToken);
    }

}
