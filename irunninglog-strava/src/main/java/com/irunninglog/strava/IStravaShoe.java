package com.irunninglog.strava;

public interface IStravaShoe {

    String getId();

    String getName();

    String getBrand();

    String getModel();

    String getDescription();

    float getDistance();

    boolean isPrimary();

    IStravaShoe setId(String id);

    IStravaShoe setName(String name);

    IStravaShoe setBrand(String brand);

    IStravaShoe setModel(String model);

    IStravaShoe setDescription(String description);

    IStravaShoe setDistance(float distance);

    IStravaShoe setPrimary(boolean primary);

}
