package com.irunninglog.spring.strava;

import java.util.ArrayList;
import java.util.List;

class StravaApiDataAthlete {

    private long id;
    private String email;
    private String firstname;
    private String lastname;
    private String profile_medium;
    private final List<StravaApiDataShoe> shoes = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfile_medium() {
        return profile_medium;
    }

    public void setProfile_medium(String profile_medium) {
        this.profile_medium = profile_medium;
    }

    public List<StravaApiDataShoe> getShoes() {
        return shoes;
    }

}
