package com.irunninglog.security;

public class AuthnRequest {

    private String username;
    private String password;
    private String path;

    public String getUsername() {
        return username;
    }

    public AuthnRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthnRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPath() {
        return path;
    }

    public AuthnRequest setPath(String path) {
        this.path = path;
        return this;
    }

}
