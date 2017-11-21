package com.irunninglog.spring.challenges;

import com.irunninglog.api.Progress;
import com.irunninglog.api.challenges.IChallenge;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Challenge implements IChallenge {

    private String name;
    private String description;
    private String distanceTotal;
    private String distanceDone;
    private int distanceInt;
    private int percentage;
    private Progress progress;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDistanceTotal() {
        return distanceTotal;
    }

    @Override
    public String getDistanceDone() {
        return distanceDone;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public Progress getProgress() {
        return progress;
    }

    @Override
    public int getDistanceInt() {
        return distanceInt;
    }

    @Override
    public IChallenge setDistanceInt(int distanceInt) {
        this.distanceInt = distanceInt;
        return this;
    }

    @Override
    public IChallenge setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IChallenge setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IChallenge setDistanceTotal(String distance) {
        this.distanceTotal = distance;
        return this;
    }

    @Override
    public IChallenge setDistanceDone(String distance) {
        this.distanceDone = distance;
        return this;
    }

    @Override
    public IChallenge setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

    @Override
    public IChallenge setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

}
