package com.irunninglog.spring.strava;

import java.time.ZonedDateTime;

class StravaApiEntryActivityCreate {

    private float distance;
    private int elapsed_time;
    private String type;
    private String name;
    private ZonedDateTime start_date_local;

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }

    public void setElapsed_time(int elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public int getElapsed_time() {
        return elapsed_time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStart_date_local(ZonedDateTime start_date_local) {
        this.start_date_local = start_date_local;
    }

    public ZonedDateTime getStart_date_local() {
        return start_date_local;
    }

}
