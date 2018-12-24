package com.irunninglog.strava;

import com.irunninglog.api.runs.IRun;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.StravaStatistics;

import java.util.List;

public interface IStravaRemoteApi {

    IStravaAthlete athlete();

    List<IRun> activities(int page, int max);

    IStravaShoe gear(String id);

    StravaStatistics statistics(long id);

    StravaActivity create(StravaActivity activity);

    StravaActivity update(long id, StravaActivityUpdate update);

    void setToken(String token);

}
