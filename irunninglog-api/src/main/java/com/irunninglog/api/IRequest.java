package com.irunninglog.api;

import com.irunninglog.api.security.IUser;

import java.util.Map;

public interface IRequest {

    IRequest setMap(Map<String, String> map);

    IRequest setUser(IUser user);

    Map<String, String> getMap();

    IUser getUser();

}
