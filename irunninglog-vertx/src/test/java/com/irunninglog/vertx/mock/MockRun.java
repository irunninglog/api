package com.irunninglog.vertx.mock;

import com.irunninglog.api.data.IRun;

public class MockRun implements IRun {

    private long id;
    private String name;
    private String description;
    private boolean dashboard;

    @Override
    public IRun setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IRun setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IRun setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IRun setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isDashboard() {
        return dashboard;
    }

}
