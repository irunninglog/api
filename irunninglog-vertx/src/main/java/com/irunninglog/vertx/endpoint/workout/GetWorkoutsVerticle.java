package com.irunninglog.vertx.endpoint.workout;

import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;
import com.irunninglog.workout.GetWorkoutsRequest;
import com.irunninglog.workout.GetWorkoutsResponse;
import com.irunninglog.workout.IWorkoutService;


@EndpointVerticle(endpoint = Endpoint.GetWorkouts)
public final class GetWorkoutsVerticle extends AbstractEndpointVerticle<GetWorkoutsRequest, GetWorkoutsResponse> {

    private final IWorkoutService workoutService;

    public GetWorkoutsVerticle(IWorkoutService workoutService) {
        super(GetWorkoutsRequest.class, GetWorkoutsResponse::new);

        this.workoutService = workoutService;
    }

    @Override
    protected GetWorkoutsResponse handle(GetWorkoutsRequest request) {
        return workoutService.get(request);
    }

}
