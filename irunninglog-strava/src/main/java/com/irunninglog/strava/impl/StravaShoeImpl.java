package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaShoe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class StravaShoeImpl implements IStravaShoe {

    private String id;
    private String name;
    private String brand;
    private String model;
    private String description;
    private float distance;
    private boolean primary;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public IStravaShoe setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IStravaShoe setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public IStravaShoe setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public IStravaShoe setModel(String model) {
        this.model = model;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IStravaShoe setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public float getDistance() {
        return distance;
    }

    @Override
    public IStravaShoe setDistance(float distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    @Override
    public IStravaShoe setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

}
