package com.irunninglog.workout;

import java.util.ArrayList;
import java.util.List;

public final class Workouts {

    private WorkoutsSummary summary;
    private WorkoutsMonth previous;
    private WorkoutsMonth next;

    private final List<Workout> workouts = new ArrayList<>();

    public WorkoutsSummary getSummary() {
        return summary;
    }

    public Workouts setSummary(WorkoutsSummary summary) {
        this.summary = summary;
        return this;
    }

    public WorkoutsMonth getPrevious() {
        return previous;
    }

    public Workouts setPrevious(WorkoutsMonth previous) {
        this.previous = previous;
        return this;
    }

    public WorkoutsMonth getNext() {
        return next;
    }

    public Workouts setNext(WorkoutsMonth next) {
        this.next = next;
        return this;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

}
