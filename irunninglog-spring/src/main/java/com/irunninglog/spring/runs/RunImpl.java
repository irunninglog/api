package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRun;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class RunImpl implements IRun {

    private int id;
    private String name;
    private String startTime;
    private String distance;
    private int duration;
    private String shoes;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IRun setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IRun setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public IRun setStartTime(String time) {
        this.startTime = time;
        return this;
    }

    @Override
    public String getDistance() {
        return distance;
    }

    @Override
    public IRun setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public IRun setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getShoes() {
        return shoes;
    }

    @Override
    public IRun setShoes(String shoes) {
        this.shoes = shoes;
        return this;
    }

}
