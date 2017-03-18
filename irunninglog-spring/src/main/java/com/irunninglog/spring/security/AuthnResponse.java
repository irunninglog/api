package com.irunninglog.spring.security;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class AuthnResponse extends AbstractResponse<User, AuthnResponse> implements IAuthnResponse<User, AuthnResponse> {

    private String token;

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = User.class)
    public User getBody() {
        return body();
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public AuthnResponse setToken(String token) {
        this.token = token;
        return this;
    }

}
