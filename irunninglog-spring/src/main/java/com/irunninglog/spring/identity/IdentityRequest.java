package com.irunninglog.spring.identity;

import com.irunninglog.api.identity.IIdentityRequest;
import com.irunninglog.spring.AbstractRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class IdentityRequest extends AbstractRequest<IdentityRequest> implements IIdentityRequest<IdentityRequest> {

    private String username;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public IdentityRequest setUsername(String username) {
        this.username = username;
        return this;
    }

}
