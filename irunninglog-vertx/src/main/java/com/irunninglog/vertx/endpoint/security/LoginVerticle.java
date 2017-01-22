package com.irunninglog.vertx.endpoint.security;

import com.irunninglog.security.Login;
import com.irunninglog.security.LoginRequest;
import com.irunninglog.security.LoginResponse;
import com.irunninglog.security.User;
import com.irunninglog.service.Endpoint;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.service.ResponseStatusException;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.Login)
public class LoginVerticle extends AbstractEndpointVerticle<LoginRequest, LoginResponse> {

    public LoginVerticle() {
        super(LoginRequest.class, LoginResponse::new);
    }

    @Override
    protected LoginResponse handle(LoginRequest request) {
        User user = request.getUser();
        if (user == null) {
            logger.error("Not user found, throwing an exception");
            throw new ResponseStatusException(ResponseStatus.Unauthenticated);
        }

        return new LoginResponse()
                .setStatus(ResponseStatus.Ok)
                .setBody(new Login()
                        .setId(user.getId())
                        .setName(user.getUsername())
                        .addRoles(user.getAuthorities()));
    }

}
