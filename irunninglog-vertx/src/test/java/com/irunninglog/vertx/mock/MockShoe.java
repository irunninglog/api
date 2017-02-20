package com.irunninglog.vertx.mock;

import com.irunninglog.api.data.IShoe;

public class MockShoe implements IShoe {

    private long id;
    private String name;
    private String description;
    private boolean dashboard;
    private String startDate;
    private String max;

    @Override
    public IShoe setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IShoe setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IShoe setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IShoe setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IShoe setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public IShoe setMax(String max) {
        this.max = max;
        return this;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

}
