package com.irunninglog.spring.runs;

import com.irunninglog.api.runs.IRun;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
@Scope("prototype")
final class RunImpl implements IRun {

    private int id;
    private ZonedDateTime startTime;
    private LocalDateTime startTimeLocal;
    private String timezone;
    private float distance;
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
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    @Override
    public IRun setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getStartTimeLocal() {
        return startTimeLocal;
    }

    @Override
    public IRun setStartTimeLocal(LocalDateTime startTimeLocal) {
        this.startTimeLocal = startTimeLocal;
        return this;
    }

    @Override
    public String getTimezone() {
        return timezone;
    }

    @Override
    public IRun setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    @Override
    public float getDistance() {
        return distance;
    }

    @Override
    public IRun setDistance(float distance) {
        this.distance = distance;
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
