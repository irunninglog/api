package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaShoe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class StravaAthleteImpl implements IStravaAthlete {

    private long id;
    private String email;
    private String firstname;
    private String lastname;
    private String avatar;

    private final List<IStravaShoe> shoes = new ArrayList<>();

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public IStravaAthlete setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IStravaAthlete setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public IStravaAthlete setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    @Override
    public IStravaAthlete setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    @Override
    public IStravaAthlete setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    @Override
    public List<IStravaShoe> getShoes() {
        return shoes;
    }

}
