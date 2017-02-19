package com.irunninglog.spring.security;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.security.ILoginResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class LoginResponse extends AbstractResponse<Login, LoginResponse> implements ILoginResponse<Login, LoginResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Long.class)
    public Login getBody() {
        return body();
    }

}
