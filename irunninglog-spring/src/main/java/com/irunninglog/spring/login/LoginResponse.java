package com.irunninglog.spring.login;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.login.ILoginResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class LoginResponse extends AbstractResponse<Login, LoginResponse> implements ILoginResponse<Login, LoginResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Login.class)
    public Login getBody() {
        return body();
    }

}
