package com.irunninglog.vertx.forbidden;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IForbiddenRequest;
import com.irunninglog.api.security.IForbiddenResponse;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.FORBIDDEN, request = IForbiddenRequest.class, response = IForbiddenResponse.class)
public final class ForbiddenHandler extends AbstractRouteHandler<IForbiddenRequest, IForbiddenResponse> {

    public ForbiddenHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
