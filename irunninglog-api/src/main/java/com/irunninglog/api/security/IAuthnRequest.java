package com.irunninglog.api.security;

import com.irunninglog.api.IProfileIdRequest;
import com.irunninglog.api.Endpoint;

public interface IAuthnRequest extends IProfileIdRequest<IAuthnRequest> {

    String getToken();

    IAuthnRequest setToken(String token);

    Endpoint getEndpoint();

    IAuthnRequest setEndpoint(Endpoint endpoint);

    String getPath();

    IAuthnRequest setPath(String path);

}
