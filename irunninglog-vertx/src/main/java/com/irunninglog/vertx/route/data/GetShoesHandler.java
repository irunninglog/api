package com.irunninglog.vertx.route.data;

import com.irunninglog.data.GetDataRequest;
import com.irunninglog.data.GetShoesResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.GetShoes)
public class GetShoesHandler extends AbstractRouteHandler<GetDataRequest, GetShoesResponse> {

    public GetShoesHandler(Vertx vertx) {
        super(vertx, GetShoesResponse.class);
    }

    @Override
    protected GetDataRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("request:get:{}", profileId);

        return new GetDataRequest().setId(Integer.parseInt(profileId));
    }

}
