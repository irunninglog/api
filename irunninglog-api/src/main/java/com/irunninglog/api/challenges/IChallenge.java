package com.irunninglog.api.challenges;

import com.irunninglog.api.Progress;

public interface IChallenge {

    String getName();

    String getDescription();

    String getDistanceTotal();

    String getDistanceDone();

    int getPercentage();

    Progress getProgress();

    int getDistanceInt();

    IChallenge setName(String name);

    IChallenge setDescription(String description);

    IChallenge setDistanceTotal(String distance);

    IChallenge setDistanceDone(String distance);

    IChallenge setPercentage(int percentage);

    IChallenge setProgress(Progress progress);

    IChallenge setDistanceInt(int distance);

}
