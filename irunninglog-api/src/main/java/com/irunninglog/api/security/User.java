package com.irunninglog.api.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {

    private final String username;

    private final Set<String> authorities;

    public User(String username, String ... authorities) {
        this.username = username;

        HashSet<String> hashSet = new HashSet<>();
        Collections.addAll(hashSet, authorities);
        this.authorities = Collections.unmodifiableSet(hashSet);
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

}
