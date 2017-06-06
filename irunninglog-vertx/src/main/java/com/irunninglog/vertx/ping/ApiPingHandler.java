package com.irunninglog.vertx.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.API_PING, request = IPingRequest.class, response = IPingResponse.class)
public class ApiPingHandler  extends AbstractRouteHandler<IPingRequest, IPingResponse> {

    public ApiPingHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
