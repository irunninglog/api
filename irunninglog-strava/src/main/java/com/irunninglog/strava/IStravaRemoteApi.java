package com.irunninglog.strava;

import javastrava.api.v3.model.*;

public interface IStravaRemoteApi {

    StravaActivity[] listAuthenticatedAthleteActivities(int page, int max);

    StravaGear getGear(String id);

    StravaAthlete getAuthenticatedAthlete();

    StravaStatistics statistics(Integer id);

    StravaActivity create(StravaActivity activity);

    StravaActivity update(int id, StravaActivityUpdate update);

    void setToken(String token);

}
