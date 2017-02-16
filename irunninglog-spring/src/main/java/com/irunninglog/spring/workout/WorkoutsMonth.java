package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IWorkoutsMonth;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class WorkoutsMonth implements IWorkoutsMonth {

    private String title;
    private String date;

    @Override
    public IWorkoutsMonth setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IWorkoutsMonth setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

}
