package com.irunninglog.spring.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.ILogin;
import com.irunninglog.api.security.ILoginService;
import com.irunninglog.api.security.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final class LoginService implements ILoginService {

    private final IFactory factory;

    @Autowired
    public LoginService(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public ILogin login(IUser user) {
        return factory.get(ILogin.class)
                .setId(user.getId())
                .setName(user.getUsername())
                .setRoles(user.getAuthorities());
    }

}
