package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaService;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import org.springframework.core.env.Environment;

final class StravaServiceImpl implements IStravaService {

    private final int id;
    private final String secret;

    private final IFactory factory;

    StravaServiceImpl(Environment environment, IFactory factory) {
        this.factory = factory;

        String config = environment.getRequiredProperty("strava");
        String [] tokens = config.split("\\|");
        id = Integer.parseInt(tokens[0]);
        secret = tokens[1];
    }

    @Override
    public IUser userFromCode(String code) throws AuthnException {
        AuthorisationService service = new AuthorisationServiceImpl();
        Token token= service.tokenExchange(id, secret, code);
        return factory.get(IUser.class)
                .setId(token.getAthlete().getId())
                .setUsername(token.getAthlete().getEmail())
                .setToken(token.getToken());
    }

    @Override
    public IUser userFromToken(String token) throws AuthnException {
        Token apiToken = new Token();
        apiToken.setToken(token);
        API api = new API(apiToken);

        StravaAthlete athlete = api.getAuthenticatedAthlete();

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setToken(token);
    }

    @Override
    public IStravaAthlete athlete(IUser user) {
        return null;
    }

}
