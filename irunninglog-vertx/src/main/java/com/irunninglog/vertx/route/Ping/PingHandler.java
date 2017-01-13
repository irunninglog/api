package com.irunninglog.vertx.route.Ping;

import com.irunninglog.ping.PingRequest;
import com.irunninglog.ping.PingResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.Ping)
public class PingHandler extends AbstractRouteHandler<PingRequest, PingResponse> {

    public PingHandler(Vertx vertx) {
        super(vertx, PingResponse.class);
    }

    @Override
    protected PingRequest request(RoutingContext routingContext) {
        return new PingRequest();
    }

}
