package com.irunninglog.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Login {

    private String name;
    private long id;

    private final List<String> roles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Login setName(String name) {
        this.name = name;
        return this;
    }

    public long getId() {
        return id;
    }

    public Login setId(long id) {
        this.id = id;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Login addRoles(Collection<String> roles) {
        this.roles.addAll(roles);
        return this;
    }

}
