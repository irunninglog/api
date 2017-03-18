package com.irunninglog.vertx.route.security;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IForbiddenRequest;
import com.irunninglog.api.security.IForbiddenResponse;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.FORBIDDEN)
public final class ForbiddenHandler extends AbstractRouteHandler<IForbiddenRequest, IForbiddenResponse> {

    public ForbiddenHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IForbiddenRequest.class, IForbiddenResponse.class);
    }

}
