package com.irunninglog.spring.workout;

import com.irunninglog.api.Progress;
import com.irunninglog.api.workout.IWorkoutsSummary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@SuppressWarnings("unused")
final class WorkoutsSummary implements IWorkoutsSummary {

    private String title;
    private int count;
    private String mileage;
    private Progress progress;
    private int percentage;

    @Override
    public IWorkoutsSummary setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IWorkoutsSummary setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public IWorkoutsSummary setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    @Override
    public IWorkoutsSummary setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

    @Override
    public IWorkoutsSummary setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public String getMileage() {
        return mileage;
    }

    public Progress getProgress() {
        return progress;
    }

    public int getPercentage() {
        return percentage;
    }

}
