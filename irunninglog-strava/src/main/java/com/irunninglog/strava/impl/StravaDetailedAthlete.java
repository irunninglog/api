package com.irunninglog.strava.impl;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
class StravaDetailedAthlete {

    private long id;
    private String email;
    private String firstname;
    private String lastname;
    private String profile_medium;
    private final List<StravaDetailedGear> shoes = new ArrayList<>();

    public List<StravaDetailedGear> getShoes() {
        return shoes;
    }

    String getProfile_medium() {
        return profile_medium;
    }

    public void setProfile_medium(String profile_medium) {
        this.profile_medium = profile_medium;
    }

    String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
