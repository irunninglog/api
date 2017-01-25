package com.irunninglog.vertx.route.data;

import com.irunninglog.data.GetShoesResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetShoes)
public class GetShoesHandler extends AbstractGetDataHandler<GetShoesResponse> {

    public GetShoesHandler(Vertx vertx) {
        super(vertx, GetShoesResponse.class);
    }

}
