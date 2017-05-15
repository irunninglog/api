package com.irunninglog.spring.login;

import com.irunninglog.api.login.ILogin;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class Login implements ILogin {

    private long id;
    private String token;

    @Override
    public ILogin setId(long id) {
        this.id = id;

        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public ILogin setToken(String token) {
        this.token = token;

        return this;
    }

    @Override
    public String getToken() {
        return token;
    }

}
