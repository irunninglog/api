package com.irunninglog.strava.impl;

@SuppressWarnings("ALL")
class StravaSummaryActivity {

    private long id;
    private String name;
    private float distance;
    private int moving_time;
    private String start_date;
    private String gear_id;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String getGear_id() {
        return gear_id;
    }

    void setGear_id(String gear_id) {
        this.gear_id = gear_id;
    }

    String getStart_date() {
        return start_date;
    }

    void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    int getMoving_time() {
        return moving_time;
    }

    void setMoving_time(int moving_time) {
        this.moving_time = moving_time;
    }

    float getDistance() {
        return distance;
    }

    void setDistance(float distance) {
        this.distance = distance;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

}
