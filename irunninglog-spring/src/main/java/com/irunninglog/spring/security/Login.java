package com.irunninglog.spring.security;

import com.irunninglog.api.security.ILogin;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@SuppressWarnings("unused")
final class Login implements ILogin {

    private long id;
    private String name;
    private List<String> authorities;

    @Override
    public ILogin setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ILogin setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ILogin setRoles(List<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

}
