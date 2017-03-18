package com.irunninglog.vertx.endpoint.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IPutWorkoutRequest;
import com.irunninglog.api.workout.IPutWorkoutResponse;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.WORKOUT_PUT)
public class PutWorkoutVerticle extends AbstractEndpointVerticle<IPutWorkoutRequest, IPutWorkoutResponse> {

    private final IWorkoutService workoutService;

    public PutWorkoutVerticle(IFactory factory, IMapper mapper, IWorkoutService workoutService) {
        super(factory, mapper, IPutWorkoutRequest.class, IPutWorkoutResponse.class);

        this.workoutService = workoutService;
    }

    @Override
    protected void handle(IPutWorkoutRequest request, IPutWorkoutResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(workoutService.put(request.getProfileId(),
                request.getWorkout(),
                request.getOffset()));
    }

}
