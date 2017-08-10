package com.irunninglog.strava;

import javastrava.api.v3.auth.model.Token;

public interface IStravaTokenExchange {

    Token token(String code);

}
