package com.irunninglog.api.workout;

import com.irunninglog.api.Privacy;

public interface IWorkout {

    IWorkout setId(long id);

    IWorkout setPrivacy(Privacy privacy);

    IWorkout setDate(String date);

    IWorkout setDistance(String distance);

    IWorkout setDuration(String duration);

    IWorkout setPace(String pace);

    IWorkout setTitle(String title);

    IWorkout setRoute(IWorkoutData data);

    IWorkout setRun(IWorkoutData data);

    IWorkout setShoe(IWorkoutData data);

    String getDate();

    long getId();

    String getDistance();

    String getDuration();

    String getPace();

    String getTitle();

    Privacy getPrivacy();

    IWorkoutData getRoute();

    IWorkoutData getRun();

    IWorkoutData getShoe();
}
