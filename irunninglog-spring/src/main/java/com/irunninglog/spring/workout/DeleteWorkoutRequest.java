package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IDeleteWorkoutRequest;
import com.irunninglog.spring.AbstractProfileIdRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class DeleteWorkoutRequest extends AbstractProfileIdRequest<IDeleteWorkoutRequest> implements IDeleteWorkoutRequest {

    private long workoutId;

    @Override
    public IDeleteWorkoutRequest setWorkoutId(long id) {
        this.workoutId = id;
        return this;
    }

    @Override
    public long getWorkoutId() {
        return workoutId;
    }

}
