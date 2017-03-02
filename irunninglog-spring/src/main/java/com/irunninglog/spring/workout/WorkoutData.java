package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IWorkoutData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class WorkoutData implements IWorkoutData {

    private long id;
    private String name;
    private String description;

    @Override
    public IWorkoutData setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IWorkoutData setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IWorkoutData setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
