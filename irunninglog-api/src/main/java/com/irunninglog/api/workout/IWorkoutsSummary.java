package com.irunninglog.api.workout;

import com.irunninglog.api.Progress;

public interface IWorkoutsSummary {

    IWorkoutsSummary setTitle(String title);

    IWorkoutsSummary setCount(int count);

    IWorkoutsSummary setMileage(String mileage);

    IWorkoutsSummary setProgress(Progress progress);

    IWorkoutsSummary setPercentage(int percentage);

}
