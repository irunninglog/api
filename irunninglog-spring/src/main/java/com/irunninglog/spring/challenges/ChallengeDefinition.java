package com.irunninglog.spring.challenges;

final class ChallengeDefinition {

    private String name;
    private String desctiption;
    private float distance;

    String getName() {
        return name;
    }

    ChallengeDefinition setName(String name) {
        this.name = name;
        return this;
    }

    String getDesctiption() {
        return desctiption;
    }

    ChallengeDefinition setDesctiption(String desctiption) {
        this.desctiption = desctiption;
        return this;
    }

    float getDistance() {
        return distance;
    }

    ChallengeDefinition setDistance(float distance) {
        this.distance = distance;
        return this;
    }

}
