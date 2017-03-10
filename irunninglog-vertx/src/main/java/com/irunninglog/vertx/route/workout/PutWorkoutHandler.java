package com.irunninglog.vertx.route.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IPutWorkoutRequest;
import com.irunninglog.api.workout.IPutWorkoutResponse;
import com.irunninglog.api.workout.IWorkoutEntry;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.PutWorkout)
public class PutWorkoutHandler extends AbstractProfileIdRouteHandler<IPutWorkoutRequest, IPutWorkoutResponse> {

    private final IMapper mapper;

    public PutWorkoutHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IPutWorkoutRequest.class, IPutWorkoutResponse.class);

        this.mapper = mapper;
    }

    @Override
    protected void populateRequest(IPutWorkoutRequest request, RoutingContext routingContext) {
        super.populateRequest(request, routingContext);

        request.setWorkout(mapper.decode(routingContext.getBodyAsString(), IWorkoutEntry.class));
    }

}
