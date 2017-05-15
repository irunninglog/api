package com.irunninglog.spring.login;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.login.ILogin;
import com.irunninglog.api.login.ILoginService;
import com.irunninglog.spring.service.ApiService;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import org.springframework.beans.factory.annotation.Autowired;

@ApiService
class LoginService implements ILoginService {

    private final IFactory factory;

    @Autowired
    LoginService(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public ILogin login(String code) {
        AuthorisationService service = new AuthorisationServiceImpl();
        Token token= service.tokenExchange(17706, "69b4db668701cb237347b0adf810a3dcf9f76527", code);
        return factory.get(ILogin.class).setId(token.getAthlete().getId()).setToken(token.getToken());
    }

}
