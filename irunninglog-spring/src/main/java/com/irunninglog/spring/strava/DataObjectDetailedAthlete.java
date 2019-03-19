package com.irunninglog.spring.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

final class DataObjectDetailedAthlete {

    private long id;
    private String email;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("profile_medium")
    private String profileMedium;
    private List<DataObjectDetailedGear> shoes;

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getProfileMedium() {
        return profileMedium;
    }

    void setProfileMedium(String profileMedium) {
        this.profileMedium = profileMedium;
    }

    List<DataObjectDetailedGear> getShoes() {
        return shoes;
    }

    void setShoes(List<DataObjectDetailedGear> shoes) {
        this.shoes = shoes;
    }

}
