package com.irunninglog.api.shoes;

public interface IShoe {

    String getId();

    String getName();

    String getBrand();

    String getModel();

    String getDescription();

    float getDistance();

    boolean isPrimary();

    IShoe setId(String id);

    IShoe setName(String name);

    IShoe setBrand(String brand);

    IShoe setModel(String model);

    IShoe setDesription(String description);

    IShoe setDistance(float distance);

    IShoe setPrimary(boolean primary);

}
