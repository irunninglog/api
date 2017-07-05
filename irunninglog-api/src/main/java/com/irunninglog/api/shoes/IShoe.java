package com.irunninglog.api.shoes;

import com.irunninglog.api.Progress;

public interface IShoe {

    String getId();

    String getName();

    String getBrand();

    String getModel();

    String getDescription();

    String getDistance();

    boolean isPrimary();

    int getPercentage();

    Progress getProgress();

    IShoe setId(String id);

    IShoe setName(String name);

    IShoe setBrand(String brand);

    IShoe setModel(String model);

    IShoe setDescription(String description);

    IShoe setDistance(String distance);

    IShoe setPrimary(boolean primary);

    IShoe setProgress(Progress progress);

    IShoe setPercentage(int perentage);

}
