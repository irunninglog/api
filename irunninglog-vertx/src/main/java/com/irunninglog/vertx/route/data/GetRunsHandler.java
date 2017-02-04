package com.irunninglog.vertx.route.data;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetRuns)
public final class GetRunsHandler extends AbstractProfileIdRouteHandler<IGetDataRequest, IGetRunsResponse> {

    public GetRunsHandler(Vertx vertx, IFactory factory) {
        super(vertx, factory, IGetDataRequest.class, IGetRunsResponse.class);
    }

}
