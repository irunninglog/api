package com.irunninglog.spring.profile;

import com.irunninglog.api.profile.IProfile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Profile implements IProfile {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String avatar;

    @Override
    public IProfile setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IProfile setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public IProfile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public IProfile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public IProfile setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

}
