package com.irunninglog.spring.workout;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.workout.IDeleteWorkoutResponse;
import com.irunninglog.api.workout.IWorkout;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class DeleteWorkoutResponse extends AbstractResponse<IWorkout, DeleteWorkoutResponse> implements IDeleteWorkoutResponse<DeleteWorkoutResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Workout.class)
    public IWorkout getBody() {
        return body();
    }

}
