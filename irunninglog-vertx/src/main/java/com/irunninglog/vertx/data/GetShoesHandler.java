package com.irunninglog.vertx.data;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.SHOES_GET, request = IGetDataRequest.class, response = IGetShoesResponse.class)
public final class GetShoesHandler extends AbstractProfileIdRouteHandler<IGetDataRequest, IGetShoesResponse> {

    public GetShoesHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
