package com.irunninglog.spring.shoes;

import com.irunninglog.api.Progress;
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
    private int percentage;
    private Progress progress;
    private String distance;
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
    public IShoe setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IShoe setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    @Override
    public String getDistance() {
        return distance;
    }

    @Override
    public IShoe setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public IShoe setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

    @Override
    public Progress getProgress() {
        return progress;
    }

    @Override
    public IShoe setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

}
