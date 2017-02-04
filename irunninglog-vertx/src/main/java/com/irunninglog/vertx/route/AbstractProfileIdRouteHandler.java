package com.irunninglog.vertx.route;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.IProfileIdRequest;
import com.irunninglog.api.IResponse;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractProfileIdRouteHandler<Q extends IProfileIdRequest<Q>, R extends IResponse> extends AbstractRouteHandler<Q, R> {

    public AbstractProfileIdRouteHandler(Vertx vertx, IFactory factory, Class<Q> requestClass, Class<R> responseClass) {
        super(vertx, factory, requestClass, responseClass);
    }

    @Override
    protected void request(Q request, RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("profile:get:{}", profileId);

        request.setProfileId(Long.parseLong(profileId));

        populateRequest(request, routingContext);
    }

    protected void populateRequest(Q request, RoutingContext routingContext) {
        // Empty for subclasses
    }

}
