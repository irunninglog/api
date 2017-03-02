package com.irunninglog.spring.workout;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.workout.IWorkout;
import com.irunninglog.api.workout.IWorkouts;
import com.irunninglog.api.workout.IWorkoutsMonth;
import com.irunninglog.api.workout.IWorkoutsSummary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@SuppressWarnings("unused")
final class Workouts implements IWorkouts {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutsSummary.class)
    private IWorkoutsSummary summary;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutsMonth.class)
    private IWorkoutsMonth previous;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutsMonth.class)
    private IWorkoutsMonth next;

    private final List<IWorkout> workouts = new ArrayList<>();

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

    @Override
    public IWorkoutsSummary getSummary() {
        return summary;
    }

    @Override
    public IWorkoutsMonth getPrevious() {
        return previous;
    }

    @Override
    public IWorkoutsMonth getNext() {
        return next;
    }

}
