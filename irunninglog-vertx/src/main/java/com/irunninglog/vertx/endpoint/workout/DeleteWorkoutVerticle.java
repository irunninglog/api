package com.irunninglog.vertx.endpoint.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.workout.IDeleteWorkoutRequest;
import com.irunninglog.api.workout.IDeleteWorkoutResponse;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.endpoint.AbstractProfileIdEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.WORKOUT_DELETE)
public class DeleteWorkoutVerticle extends AbstractProfileIdEndpointVerticle<IDeleteWorkoutRequest, IDeleteWorkoutResponse> {

    private final IWorkoutService workoutService;

    public DeleteWorkoutVerticle(IFactory factory, IMapper mapper, IWorkoutService workoutService) {
        super(factory, mapper, IDeleteWorkoutRequest.class, IDeleteWorkoutResponse.class);

        this.workoutService = workoutService;
    }

    @Override
    protected void handle(IDeleteWorkoutRequest request, IDeleteWorkoutResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK)
                .setBody(workoutService.delete(request.getProfileId(), request.getWorkoutId()));
    }

    @Override
    protected boolean authorized(IUser user, IDeleteWorkoutRequest request) {
        return matches(user, request) && (admin(user) || workoutService.ownedBy(user.getId(), request.getWorkoutId()));
    }

}
