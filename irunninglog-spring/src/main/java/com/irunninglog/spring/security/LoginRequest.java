package com.irunninglog.spring.security;

import com.irunninglog.api.security.ILoginRequest;
import com.irunninglog.spring.AbstractRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class LoginRequest extends AbstractRequest<LoginRequest> implements ILoginRequest<LoginRequest, User> {

    private User user;

    @Override
    public ILoginRequest setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public User getUser() {
        return user;
    }

}
