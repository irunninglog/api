package com.irunninglog.vertx.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.PING)
public final class PingHandler extends AbstractRouteHandler {

    public PingHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
