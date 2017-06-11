package com.irunninglog.spring;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.security.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
public final class DefaultRequest implements IRequest {

    private Map<String, String> map;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = User.class)
    private IUser user;

    @Override
    public IRequest setMap(Map<String, String> map) {
        this.map = map;
        return this;
    }

    @Override
    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public IRequest setUser(IUser user) {
        this.user = user;
        return this;
    }

    @Override
    public IUser getUser() {
        return user;
    }
}
