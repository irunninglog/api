package com.irunninglog.api.workout;

import java.util.List;

public interface IWorkouts {

    IWorkouts setSummary(IWorkoutsSummary summary);

    IWorkouts setPrevious(IWorkoutsMonth month);

    IWorkouts setNext(IWorkoutsMonth month);

    List<IWorkout> getWorkouts();

    IWorkoutsSummary getSummary();

    IWorkoutsMonth getPrevious();

    IWorkoutsMonth getNext();
}
