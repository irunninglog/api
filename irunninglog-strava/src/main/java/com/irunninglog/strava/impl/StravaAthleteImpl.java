package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaAthlete;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class StravaAthleteImpl implements IStravaAthlete {

    private int id;
    private String email;
    private String firstname;
    private String lastname;
    private String avatar;

    @Override
    public int getId() {
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
    public IStravaAthlete setId(int id) {
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

}
