package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaRemoteApi;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.*;
import javastrava.api.v3.rest.API;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class StravaRemoteApiImpl implements IStravaRemoteApi {

    private API api;

    @Override
    public final StravaActivity[] listAuthenticatedAthleteActivities(int page, int max) {
        return api.listAuthenticatedAthleteActivities(null, null, page, max);
    }

    @Override
    public final StravaGear getGear(String id) {
        return api.getGear(id);
    }

    @Override
    public final StravaAthlete getAuthenticatedAthlete() {
        return api.getAuthenticatedAthlete();
    }

    @Override
    public final StravaStatistics statistics(Integer id) {
        return api.statistics(id);
    }

    @Override
    public final StravaActivity create(StravaActivity activity) {
        return api.createManualActivity(activity);
    }

    @Override
    public final StravaActivity update(int id, StravaActivityUpdate update) {
        return api.updateActivity(id, update);
    }

    @Override
    public final void setToken(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);
        api = new API(apiToken);
    }

}
