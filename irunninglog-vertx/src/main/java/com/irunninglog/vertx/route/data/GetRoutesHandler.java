package com.irunninglog.vertx.route.data;

import com.irunninglog.data.GetRoutesResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetRoutes)
public final class GetRoutesHandler extends AbstractGetDataHandler<GetRoutesResponse> {

    public GetRoutesHandler(Vertx vertx) {
        super(vertx, GetRoutesResponse.class);
    }

}
