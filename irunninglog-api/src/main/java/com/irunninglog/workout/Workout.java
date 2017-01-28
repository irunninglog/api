package com.irunninglog.workout;

import com.irunninglog.Privacy;

@SuppressWarnings("unused")
public final class Workout {

    private long id;
    private String title;
    private String date;
    private String distance;
    private String duration;
    private String pace;
    private Privacy privacy;

    private WorkoutData route;
    private WorkoutData run;
    private WorkoutData shoe;

    public long getId() {
        return id;
    }

    public Workout setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Workout setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Workout setDate(String date) {
        this.date = date;
        return this;
    }

    public String getDistance() {
        return distance;
    }

    public Workout setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public Workout setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getPace() {
        return pace;
    }

    public Workout setPace(String pace) {
        this.pace = pace;
        return this;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public Workout setPrivacy(Privacy privacy) {
        this.privacy = privacy;
        return this;
    }

    public WorkoutData getRoute() {
        return route;
    }

    public Workout setRoute(WorkoutData route) {
        this.route = route;
        return this;
    }

    public WorkoutData getRun() {
        return run;
    }

    public Workout setRun(WorkoutData run) {
        this.run = run;
        return this;
    }

    public WorkoutData getShoe() {
        return shoe;
    }

    public Workout setShoe(WorkoutData shoe) {
        this.shoe = shoe;
        return this;
    }

}
