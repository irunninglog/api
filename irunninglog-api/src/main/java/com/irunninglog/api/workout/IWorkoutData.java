package com.irunninglog.api.workout;

public interface IWorkoutData {

    IWorkoutData setId(long id);

    IWorkoutData setName(String name);

    IWorkoutData setDescription(String description);

    long getId();

    String getName();

    String getDescription();
}
