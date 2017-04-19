package com.irunninglog.vertx;

import com.irunninglog.api.IProfileIdRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractProfileIdRouteHandler<Q extends IProfileIdRequest<Q>, R extends IResponse> extends AbstractRouteHandler<Q, R> {

    public AbstractProfileIdRouteHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

    @Override
    protected final void request(Q request, RoutingContext routingContext) {
        super.request(request, routingContext);

        String profileId = routingContext.pathParam("profileid");

        logger.info("profile:get:{}", profileId);

        request.setProfileId(Long.parseLong(profileId));

        populateRequest(request, routingContext);
    }

    protected void populateRequest(Q request, RoutingContext routingContext) {
        // Empty for subclasses
    }

}
