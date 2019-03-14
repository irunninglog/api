package com.irunninglog.spring.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

final class DataObjectSummaryActivity {

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

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    float getDistance() {
        return distance;
    }

    void setDistance(float distance) {
        this.distance = distance;
    }

    int getMovingTime() {
        return movingTime;
    }

    void setMovingTime(int movingTime) {
        this.movingTime = movingTime;
    }

    String getStartDate() {
        return startDate;
    }

    void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    String getGearId() {
        return gearId;
    }

    void setGearId(String gearId) {
        this.gearId = gearId;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

}
