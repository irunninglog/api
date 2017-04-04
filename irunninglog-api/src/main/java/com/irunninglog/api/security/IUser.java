package com.irunninglog.api.security;

import java.util.List;

public interface IUser {

    IUser setId(long id);

    IUser setUsername(String username);

    IUser setAuthorities(List<String> authorities);

    String getUsername();

    long getId();

    List<String> getAuthorities();

    boolean hasAuthority(String authority);

}
