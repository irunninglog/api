package com.irunninglog.strava;

public interface IStravaSessionCache {

    IStravaSession create(String token);

    IStravaSession get(String token);

}
