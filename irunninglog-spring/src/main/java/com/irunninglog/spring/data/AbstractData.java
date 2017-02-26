package com.irunninglog.spring.data;

import com.irunninglog.api.data.IData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractData<T extends IData> implements IData<T> {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    private long id;
    private String name;
    private String description;
    private boolean dashboard;

    public final long getId() {
        return id;
    }

    @Override
    public final T setId(long id) {
        this.id = id;
        return myself;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final T setName(String name) {
        this.name = name;
        return myself;
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final T setDescription(String description) {
        this.description = description;
        return myself;
    }

    @Override
    public final boolean isDashboard() {
        return dashboard;
    }

    @Override
    public final T setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
        return myself;
    }

}
