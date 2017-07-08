package com.irunninglog.vertx.challenges;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.EndpointHandler;
import io.vertx.core.Vertx;

@EndpointHandler(endpoint = Endpoint.GET_CHALLENGES)
public class GetChallengesHandler extends AbstractRouteHandler {

    public GetChallengesHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
