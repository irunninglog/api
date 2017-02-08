package com.irunninglog.api.workout;

public interface IWorkoutService {

    IWorkouts get(long profileId, String date, int offset);

}
