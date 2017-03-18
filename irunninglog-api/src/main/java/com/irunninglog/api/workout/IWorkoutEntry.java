package com.irunninglog.api.workout;

public interface IWorkoutEntry {

    long getId();

    IWorkoutEntry setId(long id);

    long getDuration();

    String getDistance();

    String getDate();

    IWorkoutEntry setDate(String date);

    IWorkoutEntry setDuration(long duration);

    IWorkoutEntry setDistance(String distance);

    long getRouteId();

    long getShoeId();

    long getRunId();

    IWorkoutEntry setRouteId(long id);

    IWorkoutEntry setRunId(long id);

    IWorkoutEntry setShoeId(long id);

}
