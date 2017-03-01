package com.irunninglog.vertx.mock;

import com.irunninglog.api.security.ILogin;

import java.util.List;

final class MockLogin implements ILogin {

    private long id;
    private String name;
    private List<String> roles;

    @Override
    public ILogin setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ILogin setName(String username) {
        this.name = username;
        return this;
    }

    @Override
    public ILogin setRoles(List<String> authorities) {
        this.roles = authorities;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getAuthorities() {
        return roles;
    }

}
