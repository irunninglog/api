package com.irunninglog.vertx.route.data;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetShoes)
public final class GetShoesHandler extends AbstractProfileIdRouteHandler<IGetDataRequest, IGetShoesResponse> {

    public GetShoesHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IGetDataRequest.class, IGetShoesResponse.class);
    }

}
