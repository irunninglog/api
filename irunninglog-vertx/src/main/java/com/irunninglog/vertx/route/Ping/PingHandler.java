package com.irunninglog.vertx.route.Ping;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.Ping)
public final class PingHandler extends AbstractRouteHandler<IPingRequest, IPingResponse> {

    public PingHandler(Vertx vertx, IFactory factory) {
        super(vertx, factory, IPingRequest.class,  IPingResponse.class);
    }

    @Override
    protected void request(IPingRequest request, RoutingContext routingContext) {
        // Nothing to do here
    }

}
