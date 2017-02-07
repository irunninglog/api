package com.irunninglog.vertx.route.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.ILoginRequest;
import com.irunninglog.api.security.ILoginResponse;
import com.irunninglog.api.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.Login)
public class LoginHandler extends AbstractRouteHandler<ILoginRequest, ILoginResponse> {

    public LoginHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, ILoginRequest.class, ILoginResponse.class);
    }

    @Override
    protected void request(ILoginRequest request, RoutingContext routingContext) {
        request.setUser(routingContext.get("user"));
    }

}
