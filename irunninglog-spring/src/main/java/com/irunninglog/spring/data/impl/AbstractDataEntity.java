package com.irunninglog.spring.data.impl;

import com.irunninglog.spring.jpa.AbstractEntityWithProfile;

import javax.persistence.*;

@MappedSuperclass
@SuppressWarnings({"unused", "WeakerAccess"})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractDataEntity extends AbstractEntityWithProfile {

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean dashboard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDashboard() {
        return dashboard;
    }

    public void setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
    }

}
