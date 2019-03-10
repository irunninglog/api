package com.irunninglog.spring.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

class StravaApiDataToken {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    private StravaApiDataAthlete athlete;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public StravaApiDataAthlete getAthlete() {
        return athlete;
    }

    public void setAthlete(StravaApiDataAthlete athlete) {
        this.athlete = athlete;
    }

}
