package com.irunninglog.vertx;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.security.IUser;

public final class Envelope {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private IUser user;
    private String request;

    public IUser getUser() {
        return user;
    }

    public Envelope setUser(IUser user) {
        this.user = user;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public Envelope setRequest(String request) {
        this.request = request;
        return this;
    }

}
