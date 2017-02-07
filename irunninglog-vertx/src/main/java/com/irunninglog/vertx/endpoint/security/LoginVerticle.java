package com.irunninglog.vertx.endpoint.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.ILoginRequest;
import com.irunninglog.api.security.ILoginResponse;
import com.irunninglog.api.security.ILoginService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.Login)
public class LoginVerticle extends AbstractEndpointVerticle<ILoginRequest, ILoginResponse> {

    private final ILoginService loginService;

    public LoginVerticle(IFactory factory, IMapper mapper, ILoginService loginService) {
        super(factory, mapper, ILoginRequest.class, ILoginResponse.class);

        this.loginService = loginService;
    }

    @Override
    protected void handle(ILoginRequest request, ILoginResponse response) {
        IUser user = request.getUser();

        response.setStatus(ResponseStatus.Ok)
                .setBody(loginService.login(user));
    }

}
