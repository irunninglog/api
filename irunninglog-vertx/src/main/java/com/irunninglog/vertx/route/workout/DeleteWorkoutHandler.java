package com.irunninglog.vertx.route.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IDeleteWorkoutRequest;
import com.irunninglog.api.workout.IDeleteWorkoutResponse;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.WORKOUT_DELETE)
public class DeleteWorkoutHandler extends AbstractProfileIdRouteHandler<IDeleteWorkoutRequest, IDeleteWorkoutResponse> {

    public DeleteWorkoutHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IDeleteWorkoutRequest.class, IDeleteWorkoutResponse.class);
    }

    @Override
    protected void populateRequest(IDeleteWorkoutRequest request, RoutingContext routingContext) {
        super.populateRequest(request, routingContext);

        String id = routingContext.pathParam("workoutid");
        request.setWorkoutId(Long.parseLong(id));
    }

}
