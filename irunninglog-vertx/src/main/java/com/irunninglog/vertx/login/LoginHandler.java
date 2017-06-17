package com.irunninglog.vertx.login;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.EndpointHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@EndpointHandler(endpoint = Endpoint.LOGIN)
public class LoginHandler extends AbstractRouteHandler {

    public LoginHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

    @Override
    protected void request(IRequest request, RoutingContext routingContext) {
        super.request(request, routingContext);

        request.getMap().put("code", routingContext.request().getParam("code"));
    }

}
