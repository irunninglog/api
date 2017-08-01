package com.irunninglog.spring.security;

import com.google.common.base.MoreObjects;
import com.irunninglog.api.security.IUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class User implements IUser {

    private long id;
    private String username;
    private String token;

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
    public IUser setToken(String token) {
        this.token = token;
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
    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("token", token == null ? null : "*****")
                .toString();
    }

}
