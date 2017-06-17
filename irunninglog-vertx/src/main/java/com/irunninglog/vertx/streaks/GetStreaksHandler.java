package com.irunninglog.vertx.streaks;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.EndpointHandler;
import io.vertx.core.Vertx;

@EndpointHandler(endpoint = Endpoint.GET_STREAKS)
public class GetStreaksHandler extends AbstractRouteHandler {

    public GetStreaksHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
