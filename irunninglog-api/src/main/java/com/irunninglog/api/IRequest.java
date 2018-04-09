package com.irunninglog.api;

import com.irunninglog.api.security.IUser;

import java.util.Map;

public interface IRequest {

    IRequest setMap(Map<String, String> map);

    IRequest setUser(IUser user);

    IRequest setOffset(int offset);

    IRequest setBody(String body);

    Map<String, String> getMap();

    IUser getUser();

    int getOffset();

    String getBody();

}
