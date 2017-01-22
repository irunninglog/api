package com.irunninglog.vertx.route.security;

import com.irunninglog.security.LoginRequest;
import com.irunninglog.security.LoginResponse;
import com.irunninglog.security.User;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.Login)
public class LoginHandler extends AbstractRouteHandler<LoginRequest, LoginResponse> {

    public LoginHandler(Vertx vertx) {
        super(vertx, LoginResponse.class);
    }

    @Override
    protected LoginRequest request(RoutingContext routingContext) {
        return new LoginRequest().setUser(routingContext.get("user"));
    }

}
