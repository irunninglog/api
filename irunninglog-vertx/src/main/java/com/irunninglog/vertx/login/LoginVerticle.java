package com.irunninglog.vertx.login;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.LOGIN)
public class LoginVerticle extends AbstractRequestResponseVerticle {

    private final IAuthenticationService authenticationService;

    // Public for reflection
    @SuppressWarnings("WeakerAccess")
    public LoginVerticle(IFactory factory, IMapper mapper, IAuthenticationService loginService) {
        super(factory, mapper);

        this.authenticationService = loginService;
    }

    @Override
    protected void handle(IRequest request, IResponse response) throws AuthnException {
        response.setStatus(ResponseStatus.OK).setBody(authenticationService.authenticateCode(request.getMap().get("code")));
    }

}
