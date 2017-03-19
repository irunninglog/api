package com.irunninglog.vertx.route.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.PING)
public final class PingHandler extends AbstractRouteHandler<IPingRequest, IPingResponse> {

    public PingHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IPingRequest.class,  IPingResponse.class);
    }

}
