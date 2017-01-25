package com.irunninglog.data;

@SuppressWarnings("unused")
public abstract class AbstractData<T extends AbstractData> {

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    private long id;
    private String name;
    private String description;
    private boolean dashboard;

    public long getId() {
        return id;
    }

    public T setId(long id) {
        this.id = id;
        return myself;
    }

    public String getName() {
        return name;
    }

    public T setName(String name) {
        this.name = name;
        return myself;
    }

    public String getDescription() {
        return description;
    }

    public T setDescription(String description) {
        this.description = description;
        return myself;
    }

    public boolean isDashboard() {
        return dashboard;
    }

    public T setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
        return myself;
    }

}
