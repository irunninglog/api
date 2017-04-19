package com.irunninglog.vertx.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.workout.IGetWorkoutsRequest;
import com.irunninglog.api.workout.IGetWorkoutsResponse;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.AbstractProfileIdEndpointVerticle;
import com.irunninglog.vertx.EndpointVerticle;


@EndpointVerticle(endpoint = Endpoint.WORKOUTS_GET, request = IGetWorkoutsRequest.class, response = IGetWorkoutsResponse.class)
public final class GetWorkoutsVerticle extends AbstractProfileIdEndpointVerticle<IGetWorkoutsRequest, IGetWorkoutsResponse> {

    private final IWorkoutService workoutService;

    public GetWorkoutsVerticle(IWorkoutService workoutService, IFactory factory, IMapper mapper) {
        super(factory, mapper);

        this.workoutService = workoutService;
    }

    @Override
    protected void handle(IGetWorkoutsRequest request, IGetWorkoutsResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK)
                .setBody(workoutService.get(request.getProfileId(), request.getDate(), request.getOffset()));
    }

    @Override
    protected boolean authorized(IUser user, IGetWorkoutsRequest request) {
        return matches(user, request);
    }

}
