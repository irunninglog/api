package com.irunninglog.api.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {

    private String username;

    private final Set<String> authorities = new HashSet<>();

    public User() {
        super();
    }

    public User(String username, String ... authorities) {
        this();

        this.username = username;

        Collections.addAll(this.authorities, authorities);
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

}
