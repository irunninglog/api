package com.irunninglog.vertx.login;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.login.ILoginService;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.LOGIN)
public class LoginVerticle extends AbstractRequestResponseVerticle {

    private final ILoginService loginService;

    public LoginVerticle(IFactory factory, IMapper mapper, ILoginService loginService) {
        super(factory, mapper);

        this.loginService = loginService;
    }

    @Override
    protected void handle(IRequest request, IResponse response) {
        response.setBody(loginService.login((String) request.getMap().get("code"))).setStatus(ResponseStatus.OK);
    }

}
