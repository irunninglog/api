package com.irunninglog.spring.workout;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.workout.IPutWorkoutResponse;
import com.irunninglog.api.workout.IWorkout;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class PutWorkoutResponse extends AbstractResponse<IWorkout, PutWorkoutResponse> implements IPutWorkoutResponse<PutWorkoutResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Workout.class)
    public IWorkout getBody() {
        return body();
    }

}
