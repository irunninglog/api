package com.irunninglog.spring.athletes;

import com.irunninglog.api.athletes.IAthlete;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Athlete implements IAthlete {

    private long id;
    private String email;
    private String firstname;
    private String lastname;
    private String avatar;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public IAthlete setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public IAthlete setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public IAthlete setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public IAthlete setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public IAthlete setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

}
