package com.irunninglog.vertx.mock;

import com.irunninglog.api.workout.IWorkouts;

@SuppressWarnings("unused")
public class MockWorkouts implements IWorkouts {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
