package com.irunninglog.api.login;

public interface ILogin {

    ILogin setId(long id);

    long getId();

    ILogin setToken(String token);

    String getToken();

}
