package com.irunninglog.strava;

public interface IStravaAthlete {

    int getId();

    IStravaAthlete setId(int id);

    String getEmail();

    IStravaAthlete setEmail(String email);

    String getFirstname();

    IStravaAthlete setFirstname(String firstname);

    String getLastname();

    IStravaAthlete setLastname(String lastname);

    String getAvatar();

    IStravaAthlete setAvatar(String avatar);

}
