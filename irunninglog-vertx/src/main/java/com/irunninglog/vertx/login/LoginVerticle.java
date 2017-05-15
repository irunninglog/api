package com.irunninglog.vertx.login;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.login.ILoginRequest;
import com.irunninglog.api.login.ILoginResponse;
import com.irunninglog.api.login.ILoginService;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.LOGIN, request = ILoginRequest.class, response = ILoginResponse.class)
public class LoginVerticle extends AbstractRequestResponseVerticle<ILoginRequest, ILoginResponse> {

    private final ILoginService loginService;

    public LoginVerticle(IFactory factory, IMapper mapper, ILoginService loginService) {
        super(factory, mapper);

        this.loginService = loginService;
    }

    @Override
    protected boolean authorized(IUser user, ILoginRequest request) {
        return Boolean.TRUE;
    }

    @Override
    protected void handle(ILoginRequest request, ILoginResponse response) {
        //noinspection unchecked
        response.setBody(loginService.login(request.getCode())).setStatus(ResponseStatus.OK);
    }

}
