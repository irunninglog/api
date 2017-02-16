package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.workout.IGetWorkoutsResponse;
import com.irunninglog.api.workout.IWorkouts;

class MockGetWorkoutsResponse implements IGetWorkoutsResponse<MockGetWorkoutsResponse> {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockWorkouts.class)
    private IWorkouts body;

    @Override
    public MockGetWorkoutsResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockGetWorkoutsResponse setBody(IWorkouts body) {
        this.body = body;
        return this;
    }

    @Override
    public IWorkouts getBody() {
        return body;
    }

}
