package com.irunninglog.api.identity;

public interface IIdentity {

    String getUsername();

    IIdentity setUsername(String username);

    long getId();

    IIdentity setId(long id);

}
