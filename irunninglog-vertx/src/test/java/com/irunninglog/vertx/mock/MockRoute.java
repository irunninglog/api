package com.irunninglog.vertx.mock;

import com.irunninglog.api.data.IRoute;

public class MockRoute implements IRoute {

    private long id;
    private String name;
    private String decscription;
    private boolean dashboard;

    @Override
    public IRoute setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IRoute setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IRoute setDescription(String description) {
        this.decscription = description;
        return this;
    }

    @Override
    public IRoute setDashboard(boolean dashboard) {
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
        return decscription;
    }

    @Override
    public boolean isDashboard() {
        return dashboard;
    }

}
