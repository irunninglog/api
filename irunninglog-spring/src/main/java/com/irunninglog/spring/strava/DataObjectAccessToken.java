package com.irunninglog.spring.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

final class DataObjectAccessToken {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    private DataObjectDetailedAthlete athlete;

    String getAccessToken() {
        return accessToken;
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    String getTokenType() {
        return tokenType;
    }

    void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    DataObjectDetailedAthlete getAthlete() {
        return athlete;
    }

    void setAthlete(DataObjectDetailedAthlete athlete) {
        this.athlete = athlete;
    }

}
