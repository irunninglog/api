package com.irunninglog.vertx.mock;

import com.irunninglog.api.workout.IDeleteWorkoutRequest;

final class MockDeleteWorkoutRequest implements IDeleteWorkoutRequest {

    private long profileId;
    private long workoutId;
    private int offset;

    @Override
    public IDeleteWorkoutRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IDeleteWorkoutRequest setProfileId(long profileId) {
        this.profileId = profileId;
        return this;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

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
