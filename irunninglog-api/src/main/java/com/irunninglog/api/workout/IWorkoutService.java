package com.irunninglog.api.workout;

public interface IWorkoutService {

    IWorkouts get(long profileId, String date, int offset);

    IWorkout put(long profileId, IWorkoutEntry workout, int offset);

    IWorkout delete(long profileId, long workoutId);

    boolean ownedBy(long profileId, long workoutId);

}
