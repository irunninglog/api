package com.irunninglog.vertx.route.data;

import com.irunninglog.data.GetRunsResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetRuns)
public final class GetRunsHandler extends AbstractGetDataHandler<GetRunsResponse> {

    public GetRunsHandler(Vertx vertx) {
        super(vertx, GetRunsResponse.class);
    }

}
