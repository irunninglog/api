package com.irunninglog.vertx.route.data;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.RUNS_GET)
public final class GetRunsHandler extends AbstractProfileIdRouteHandler<IGetDataRequest, IGetRunsResponse> {

    public GetRunsHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IGetDataRequest.class, IGetRunsResponse.class);
    }

}
