package com.irunninglog.vertx.route.data;

import com.irunninglog.data.GetDataRequest;
import com.irunninglog.service.AbstractResponse;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

abstract class AbstractGetDataHandler<T extends AbstractResponse> extends AbstractRouteHandler<GetDataRequest, T> {

    AbstractGetDataHandler(Vertx vertx, Class<T> responseClass) {
        super(vertx, responseClass);
    }

    @Override
    protected final GetDataRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("request:get:{}", profileId);

        return new GetDataRequest().setId(Integer.parseInt(profileId));
    }

}
