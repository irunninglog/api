package com.irunninglog.security;

import com.irunninglog.service.AbstractRequest;

public final class LoginRequest extends AbstractRequest<LoginRequest> {

    private User user;

    public LoginRequest setUser(User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

}
