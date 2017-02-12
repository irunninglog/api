package com.irunninglog.api.security;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;

public interface IAuthnRequest<T extends IAuthnRequest> extends IRequest<T> {

    String getToken();

    IAuthnRequest setToken(String token);

    Endpoint getEndpoint();

    IAuthnRequest setEndpoint(Endpoint endpoint);

    String getPath();

    IAuthnRequest setPath(String path);

}
