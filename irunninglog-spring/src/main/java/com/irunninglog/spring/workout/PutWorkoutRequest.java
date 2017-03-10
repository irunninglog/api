package com.irunninglog.spring.workout;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.workout.IPutWorkoutRequest;
import com.irunninglog.api.workout.IWorkoutEntry;
import com.irunninglog.spring.AbstractProfileIdRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class PutWorkoutRequest extends AbstractProfileIdRequest<IPutWorkoutRequest> implements IPutWorkoutRequest {

    private IWorkoutEntry workout;

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutEntry.class)
    public IPutWorkoutRequest setWorkout(IWorkoutEntry workout) {
        this.workout = workout;
        return this;
    }

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutEntry.class)
    public IWorkoutEntry getWorkout() {
        return workout;
    }

}
