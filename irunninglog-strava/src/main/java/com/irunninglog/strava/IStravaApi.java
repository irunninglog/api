package com.irunninglog.strava;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;

import java.util.List;

public interface IStravaApi {

    Token token(String code);

    StravaAthlete athlete(String token);

    List<StravaActivity> activities(String token);

    StravaGear gear(String token, String id);

}
