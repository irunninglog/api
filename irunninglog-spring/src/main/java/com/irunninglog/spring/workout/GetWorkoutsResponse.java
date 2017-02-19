package com.irunninglog.spring.workout;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.workout.IGetWorkoutsResponse;
import com.irunninglog.api.workout.IWorkouts;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetWorkoutsResponse extends AbstractResponse<IWorkouts, GetWorkoutsResponse> implements IGetWorkoutsResponse<GetWorkoutsResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Workouts.class)
    public IWorkouts getBody() {
        return body();
    }

}
