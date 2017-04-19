package com.irunninglog.vertx.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IPutWorkoutRequest;
import com.irunninglog.api.workout.IPutWorkoutResponse;
import com.irunninglog.api.workout.IWorkoutEntry;
import com.irunninglog.vertx.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.WORKOUT_PUT, request = IPutWorkoutRequest.class, response = IPutWorkoutResponse.class)
public final class PutWorkoutHandler extends AbstractProfileIdRouteHandler<IPutWorkoutRequest, IPutWorkoutResponse> {

    private final IMapper mapper;

    public PutWorkoutHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);

        this.mapper = mapper;
    }

    @Override
    protected void populateRequest(IPutWorkoutRequest request, RoutingContext routingContext) {
        super.populateRequest(request, routingContext);

        request.setWorkout(mapper.decode(routingContext.getBodyAsString(), IWorkoutEntry.class));
    }

}
