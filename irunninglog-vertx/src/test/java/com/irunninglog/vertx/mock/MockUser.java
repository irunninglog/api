package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.security.IUser;

import java.util.List;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class")
public class MockUser implements IUser {

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

    @Override
    public boolean hasAuthority(String authority) {
        return authorities != null && authorities.contains(authority);
    }

}
