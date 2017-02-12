package com.irunninglog.spring.security;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.spring.AbstractRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class AuthnRequest extends AbstractRequest<AuthnRequest> implements IAuthnRequest<AuthnRequest> {

    private Endpoint endpoint;
    private String path;
    private String token;

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public AuthnRequest setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public AuthnRequest setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public AuthnRequest setPath(String path) {
        this.path = path;
        return this;
    }

}
