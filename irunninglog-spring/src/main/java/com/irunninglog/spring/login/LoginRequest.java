package com.irunninglog.spring.login;

import com.irunninglog.api.login.ILoginRequest;
import com.irunninglog.spring.AbstractRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
class LoginRequest extends AbstractRequest<LoginRequest> implements ILoginRequest<LoginRequest> {

    private String code;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public LoginRequest setCode(String code) {
        this.code = code;

        return this;
    }

}
