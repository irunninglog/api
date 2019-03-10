package com.irunninglog.spring.strava;

public class StravaApiDataActivity {

    private long id;
    private String name;
    private float distance;
    private int movingTime;
    private String startDate;
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

    public String getStart_date() {
        return startDate;
    }

    public void setStart_date(String startDate) {
        this.startDate = startDate;
    }

    public String getGear_id() {
        return gearId;
    }

    public void setGear_id(String gearId) {
        this.gearId = gearId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
