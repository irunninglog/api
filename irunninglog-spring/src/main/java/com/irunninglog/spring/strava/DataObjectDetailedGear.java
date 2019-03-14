package com.irunninglog.spring.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

final class DataObjectDetailedGear {

    private String id;
    private String name;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("model_name")
    private String modelName;
    private String description;
    private float distance;
    private boolean primary;

    String getBrandName() {
        return brandName;
    }

    void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    String getModelName() {
        return modelName;
    }

    void setModelName(String modelName) {
        this.modelName = modelName;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    float getDistance() {
        return distance;
    }

    void setDistance(float distance) {
        this.distance = distance;
    }

    boolean isPrimary() {
        return primary;
    }

    void setPrimary(boolean primary) {
        this.primary = primary;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

}
