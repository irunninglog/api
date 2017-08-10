package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaTokenExchange;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StravaTokenExchangeImpl implements IStravaTokenExchange{

    private final int id;
    private final String secret;

    @Autowired
    StravaTokenExchangeImpl(Environment environment) {
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

}
