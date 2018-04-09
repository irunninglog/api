package com.irunninglog.spring;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.security.IUser;

import java.util.Map;

final class DefaultRequest implements IRequest {

    private Map<String, String> map;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private IUser user;
    private int offset;
    private String body;

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
    public IRequest setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("map", map)
                .add("user", user)
                .add("offset", offset)
                .add("body", body == null ? 0 : body.length())
                .toString();
    }

}
