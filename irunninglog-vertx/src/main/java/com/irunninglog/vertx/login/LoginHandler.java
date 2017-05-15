package com.irunninglog.vertx.login;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.login.ILoginRequest;
import com.irunninglog.api.login.ILoginResponse;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.LOGIN, request = ILoginRequest.class, response = ILoginResponse.class)
public class LoginHandler extends AbstractRouteHandler<ILoginRequest, ILoginResponse> {

    public LoginHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

    @Override
    protected void request(ILoginRequest request, RoutingContext routingContext) {
        super.request(request, routingContext);

        request.setCode(routingContext.request().getParam("code"));
    }

}
