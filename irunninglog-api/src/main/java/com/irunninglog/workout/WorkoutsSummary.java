package com.irunninglog.workout;

public final class WorkoutsSummary {

    private String title;
    private int count;
    private String mileage;
    private int percentage;

    public String getTitle() {
        return title;
    }

    public WorkoutsSummary setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getCount() {
        return count;
    }

    public WorkoutsSummary setCount(int count) {
        this.count = count;
        return this;
    }

    public String getMileage() {
        return mileage;
    }

    public WorkoutsSummary setMileage(String mileage) {
        this.mileage = mileage;
        return this;
    }

    public int getPercentage() {
        return percentage;
    }

    public WorkoutsSummary setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

}
