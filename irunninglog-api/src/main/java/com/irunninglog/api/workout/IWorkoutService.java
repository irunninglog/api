package com.irunninglog.api.workout;

public interface IWorkoutService {

    IWorkouts get(long profileId, int offset);

}
