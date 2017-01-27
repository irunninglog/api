package com.irunninglog.workout;

public final class WorkoutData {

    private long id;
    private String name;
    private String description;

    public long getId() {
        return id;
    }

    public WorkoutData setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkoutData setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkoutData setDescription(String description) {
        this.description = description;
        return this;
    }

}
