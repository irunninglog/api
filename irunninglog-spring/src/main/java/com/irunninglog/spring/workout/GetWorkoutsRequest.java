package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IGetWorkoutsRequest;
import com.irunninglog.spring.AbstractProfileIdRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetWorkoutsRequest extends AbstractProfileIdRequest<IGetWorkoutsRequest> implements IGetWorkoutsRequest {

    private String date;

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public IGetWorkoutsRequest setDate(String date) {
        this.date = date;
        return this;
    }

}
