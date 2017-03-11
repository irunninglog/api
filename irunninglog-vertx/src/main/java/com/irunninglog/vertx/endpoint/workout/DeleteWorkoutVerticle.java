package com.irunninglog.vertx.endpoint.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IDeleteWorkoutRequest;
import com.irunninglog.api.workout.IDeleteWorkoutResponse;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.DeleteWorkout)
public class DeleteWorkoutVerticle extends AbstractEndpointVerticle<IDeleteWorkoutRequest, IDeleteWorkoutResponse> {

    private final IWorkoutService workoutService;

    public DeleteWorkoutVerticle(IFactory factory, IMapper mapper, IWorkoutService workoutService) {
        super(factory, mapper, IDeleteWorkoutRequest.class, IDeleteWorkoutResponse.class);

        this.workoutService = workoutService;
    }

    @Override
    protected void handle(IDeleteWorkoutRequest request, IDeleteWorkoutResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.Ok)
                .setBody(workoutService.delete(request.getProfileId(), request.getWorkoutId()));
    }

}
