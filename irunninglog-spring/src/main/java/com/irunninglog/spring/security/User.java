package com.irunninglog.spring.security;

import com.irunninglog.api.security.IUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
final class User implements IUser {

    private long id;
    private String username;
    private List<String> authorities;

    @Override
    public IUser setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IUser setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public IUser setAuthorities(List<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public List<String> getAuthorities() {
        return authorities;
    }

}
