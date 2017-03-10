package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.workout.IPutWorkoutResponse;
import com.irunninglog.api.workout.IWorkout;

final class MockPutWorkoutResponse implements IPutWorkoutResponse<MockPutWorkoutResponse> {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockWorkoutEntry.class)
    private IWorkout body;

    @Override
    public MockPutWorkoutResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockPutWorkoutResponse setBody(IWorkout body) {
        this.body = body;
        return this;
    }

    @Override
    public IWorkout getBody() {
        return body;
    }

}
