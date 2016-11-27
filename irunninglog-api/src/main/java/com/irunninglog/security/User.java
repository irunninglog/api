package com.irunninglog.security;

import com.google.common.base.MoreObjects;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public final class User {

    private long id;
    private String username;

    private final Set<String> authorities = new HashSet<>();

    public User() {
        super();
    }

    public User(long id, String username, String ... authorities) {
        this();

        this.username = username;
        this.id = id;

        Collections.addAll(this.authorities, authorities);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("authorities", authorities)
                .toString();
    }

}
