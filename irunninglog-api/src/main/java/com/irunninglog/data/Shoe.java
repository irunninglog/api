package com.irunninglog.data;

public final class Shoe {

    private long id;
    private String name;
    private String description;
    private boolean dashboard;
    private String startDate;
    private String max;

    public long getId() {
        return id;
    }

    public Shoe setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Shoe setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Shoe setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isDashboard() {
        return dashboard;
    }

    public Shoe setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public Shoe setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getMax() {
        return max;
    }

    public Shoe setMax(String max) {
        this.max = max;
        return this;
    }

}
