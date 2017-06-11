package com.irunninglog.api.security;

public interface IUser {

    IUser setId(long id);

    IUser setUsername(String username);

    IUser setToken(String token);

    String getUsername();

    long getId();

    String getToken();

}
