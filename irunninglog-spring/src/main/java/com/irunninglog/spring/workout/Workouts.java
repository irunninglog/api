package com.irunninglog.spring.workout;

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

    private IWorkoutsSummary summary;
    private IWorkoutsMonth previous;
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

    public IWorkoutsSummary getSummary() {
        return summary;
    }

    public IWorkoutsMonth getPrevious() {
        return previous;
    }

    public IWorkoutsMonth getNext() {
        return next;
    }

}
