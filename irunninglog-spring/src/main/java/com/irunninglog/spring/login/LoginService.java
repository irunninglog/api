package com.irunninglog.spring.login;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.login.ILogin;
import com.irunninglog.api.login.ILoginService;
import com.irunninglog.spring.service.ApiService;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@ApiService
class LoginService implements ILoginService {

    private final IFactory factory;

    private final int id;
    private final String secret;

    @Autowired
    LoginService(Environment environment, IFactory factory) {
        this.factory = factory;

        String config = environment.getRequiredProperty("strava");
        String [] tokens = config.split("\\|");
        id = Integer.parseInt(tokens[0]);
        secret = tokens[1];
    }

    @Override
    public ILogin login(String code) {
        AuthorisationService service = new AuthorisationServiceImpl();
        Token token= service.tokenExchange(id, secret, code);
        return factory.get(ILogin.class).setId(token.getAthlete().getId()).setToken(token.getToken());
    }

}
