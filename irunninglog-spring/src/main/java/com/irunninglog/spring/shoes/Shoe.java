package com.irunninglog.spring.shoes;

import com.irunninglog.api.shoes.IShoe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Shoe implements IShoe {

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
    public String getName() {
        return name;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    @Override
    public IShoe setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public IShoe setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IShoe setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    @Override
    public IShoe setModel(String model) {
        this.model = model;
        return this;
    }

    @Override
    public IShoe setDesription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IShoe setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    @Override
    public float getDistance() {
        return distance;
    }

    @Override
    public IShoe setDistance(float distance) {
        this.distance = distance;
        return this;
    }

}
