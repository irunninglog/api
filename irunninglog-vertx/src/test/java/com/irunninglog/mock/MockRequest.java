package com.irunninglog.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.security.IUser;

import java.util.Map;

public class MockRequest implements IRequest {

    private Map<String, String> map;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockUser.class)
    private IUser user;
    private int offset;
    private String body;

    @Override
    public IRequest setMap(Map<String, String> map) {
        this.map = map;
        return this;
    }

    @Override
    public IRequest setUser(IUser user) {
        this.user = user;
        return this;
    }

    @Override
    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public IRequest setBody(String body) {
        this.body = body;
        return this;
    }
}
