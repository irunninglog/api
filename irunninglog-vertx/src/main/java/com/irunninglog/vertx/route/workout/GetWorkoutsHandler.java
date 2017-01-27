package com.irunninglog.vertx.route.workout;

import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import com.irunninglog.workout.GetWorkoutsRequest;
import com.irunninglog.workout.GetWorkoutsResponse;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.GetWorkouts)
public final class GetWorkoutsHandler extends AbstractRouteHandler<GetWorkoutsRequest, GetWorkoutsResponse> {

    public GetWorkoutsHandler(Vertx vertx) {
        super(vertx, GetWorkoutsResponse.class);
    }

    @Override
    protected GetWorkoutsRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("profile:get:{}", profileId);

        return new GetWorkoutsRequest().setId(Long.parseLong(profileId));
    }

}
