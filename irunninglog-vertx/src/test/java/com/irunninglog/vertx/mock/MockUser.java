package com.irunninglog.vertx.mock;

import com.irunninglog.api.security.IUser;

@SuppressWarnings("unused")
public class MockUser implements IUser {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
