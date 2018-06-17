package com.irunninglog.spring.challenges;

import java.math.BigDecimal;

final class ChallengeDefinition {

    private String name;
    private String desctiption;
    private BigDecimal distance;

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

    BigDecimal getDistance() {
        return distance;
    }

    ChallengeDefinition setDistance(BigDecimal distance) {
        this.distance = distance;
        return this;
    }

    @Deprecated
    ChallengeDefinition setDistance(float distance) {
        this.distance = BigDecimal.valueOf(distance);
        return this;
    }

}
