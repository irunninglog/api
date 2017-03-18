package com.irunninglog.vertx.endpoint.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IGetWorkoutsRequest;
import com.irunninglog.api.workout.IGetWorkoutsResponse;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;


@EndpointVerticle(endpoint = Endpoint.WORKOUTS_GET)
public final class GetWorkoutsVerticle extends AbstractEndpointVerticle<IGetWorkoutsRequest, IGetWorkoutsResponse> {

    private final IWorkoutService workoutService;

    public GetWorkoutsVerticle(IWorkoutService workoutService, IFactory factory, IMapper mapper) {
        super(factory, mapper, IGetWorkoutsRequest.class, IGetWorkoutsResponse.class);

        this.workoutService = workoutService;
    }

    @Override
    protected void handle(IGetWorkoutsRequest request, IGetWorkoutsResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.Ok)
                .setBody(workoutService.get(request.getProfileId(), request.getDate(), request.getOffset()));
    }

}
