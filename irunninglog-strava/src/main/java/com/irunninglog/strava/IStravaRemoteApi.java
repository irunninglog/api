package com.irunninglog.strava;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.StravaStatistics;

public interface IStravaRemoteApi {

    StravaActivity[] listAuthenticatedAthleteActivities(int page, int max);

    StravaGear getGear(String id);

    StravaAthlete getAuthenticatedAthlete();

    StravaStatistics statistics(Integer id);

    void setToken(String token);

}
