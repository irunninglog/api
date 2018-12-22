package com.irunninglog.strava;

import com.irunninglog.api.runs.IRun;
import javastrava.api.v3.model.*;

import java.util.List;

public interface IStravaRemoteApi {

    List<IRun> activities(int page, int max);

    StravaGear getGear(String id);

    StravaAthlete getAuthenticatedAthlete();

    StravaStatistics statistics(Integer id);

    StravaActivity create(StravaActivity activity);

    StravaActivity update(long id, StravaActivityUpdate update);

    void setToken(String token);

}
