package com.irunninglog.spring.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StravaApiDataActivity {

    private long id;
    private String name;
    private float distance;
    @JsonProperty("moving_time")
    private int movingTime;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("gear_id")
    private String gearId;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(int movingTime) {
        this.movingTime = movingTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getGearId() {
        return gearId;
    }

    public void setGearId(String gearId) {
        this.gearId = gearId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
