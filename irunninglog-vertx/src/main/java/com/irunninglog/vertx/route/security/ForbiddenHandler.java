package com.irunninglog.vertx.route.security;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IForbiddenRequest;
import com.irunninglog.api.security.IForbiddenResponse;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.Forbidden)
public final class ForbiddenHandler extends AbstractRouteHandler<IForbiddenRequest, IForbiddenResponse> {

    public ForbiddenHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IForbiddenRequest.class, IForbiddenResponse.class);
    }

    @Override
    protected void request(IForbiddenRequest request, RoutingContext routingContext) {

    }

}
