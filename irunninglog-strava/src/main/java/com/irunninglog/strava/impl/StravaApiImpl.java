package com.irunninglog.strava.impl;

import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaApi;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
final class StravaApiImpl implements IStravaApi {

    private final int id;
    private final String secret;

    @Autowired
    StravaApiImpl(Environment environment) {
        String config = environment.getRequiredProperty("strava");
        String [] tokens = config.split("\\|");
        id = Integer.parseInt(tokens[0]);
        secret = tokens[1];
    }

    @Override
    public Token token(String code) {
        AuthorisationService service = new AuthorisationServiceImpl();
        return service.tokenExchange(id, secret, code);
    }

    @Override
    public StravaAthlete athlete(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);
        API api = new API(apiToken);
        return api.getAuthenticatedAthlete();
    }

    @Override
    public StravaActivity[] activities(IUser user, int page) {
        Token apiToken = new Token();
        apiToken.setToken(user.getToken());

        API api = new API(apiToken);
        return api.listAuthenticatedAthleteActivities(null, null, page, 200);
    }

}
