package com.irunninglog.vertx.mock;

import com.irunninglog.api.workout.IWorkout;
import com.irunninglog.api.workout.IWorkouts;
import com.irunninglog.api.workout.IWorkoutsMonth;
import com.irunninglog.api.workout.IWorkoutsSummary;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class MockWorkouts implements IWorkouts {

    private final List<IWorkout> workouts = new ArrayList<>();

    private IWorkoutsSummary summary;
    private IWorkoutsMonth previous;
    private IWorkoutsMonth next;

    @Override
    public IWorkouts setSummary(IWorkoutsSummary summary) {
        this.summary = summary;
        return this;
    }

    @Override
    public IWorkouts setPrevious(IWorkoutsMonth month) {
        this.previous = month;
        return this;
    }

    @Override
    public IWorkouts setNext(IWorkoutsMonth month) {
        this.next = month;
        return this;
    }

    @Override
    public List<IWorkout> getWorkouts() {
        return workouts;
    }

}
