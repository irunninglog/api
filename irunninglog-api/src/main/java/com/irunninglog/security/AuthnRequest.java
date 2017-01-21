package com.irunninglog.security;

import com.irunninglog.service.AbstractProfileIdRequest;
import com.irunninglog.service.Endpoint;

public final class AuthnRequest extends AbstractProfileIdRequest<AuthnRequest> {

    private Endpoint endpoint;
    private String path;
    private String token;

    public String getToken() {
        return token;
    }

    public AuthnRequest setToken(String token) {
        this.token = token;
        return this;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public AuthnRequest setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getPath() {
        return path;
    }

    public AuthnRequest setPath(String path) {
        this.path = path;
        return this;
    }

}
