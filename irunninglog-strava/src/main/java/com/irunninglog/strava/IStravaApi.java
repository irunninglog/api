package com.irunninglog.strava;

import com.irunninglog.api.security.IUser;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;

public interface IStravaApi {

    Token token(String code);

    StravaAthlete athlete(String token);

    StravaActivity[] activities(IUser user, int page);

    StravaGear gear(IUser user, String id);

}
