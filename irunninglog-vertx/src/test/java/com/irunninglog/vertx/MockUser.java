package com.irunninglog.vertx;

import com.irunninglog.api.security.IUser;

public class MockUser implements IUser {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
