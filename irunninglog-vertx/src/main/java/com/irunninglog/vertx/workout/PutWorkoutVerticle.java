package com.irunninglog.vertx.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.workout.IPutWorkoutRequest;
import com.irunninglog.api.workout.IPutWorkoutResponse;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.AbstractProfileIdEndpointVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.WORKOUT_PUT, request = IPutWorkoutRequest.class, response = IPutWorkoutResponse.class)
public final class PutWorkoutVerticle extends AbstractProfileIdEndpointVerticle<IPutWorkoutRequest, IPutWorkoutResponse> {

    private final IWorkoutService workoutService;

    public PutWorkoutVerticle(IFactory factory, IMapper mapper, IWorkoutService workoutService) {
        super(factory, mapper);

        this.workoutService = workoutService;
    }

    @Override
    protected void handle(IPutWorkoutRequest request, IPutWorkoutResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(workoutService.put(request.getProfileId(),
                request.getWorkout(),
                request.getOffset()));
    }

    @Override
    protected boolean authorized(IUser user, IPutWorkoutRequest request) {
        return matches(user, request) && (admin(user) || workoutService.ownedBy(user.getId(), request.getWorkout().getId()));
    }

}
