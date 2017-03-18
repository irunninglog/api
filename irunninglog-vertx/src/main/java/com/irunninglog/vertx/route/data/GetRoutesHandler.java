package com.irunninglog.vertx.route.data;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRoutesResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.ROUTES_GET)
public final class GetRoutesHandler extends AbstractProfileIdRouteHandler<IGetDataRequest, IGetRoutesResponse> {

    public GetRoutesHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IGetDataRequest.class, IGetRoutesResponse.class);
    }

}
