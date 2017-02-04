package com.irunninglog.vertx.route.data;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.Endpoint;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetRoutes)
public final class GetRoutesHandler extends AbstractProfileIdRouteHandler<IGetDataRequest, IGetRoutesResponse> {

    public GetRoutesHandler(Vertx vertx, IFactory factory) {
        super(vertx, factory, IGetDataRequest.class, IGetRoutesResponse.class);
    }

}
