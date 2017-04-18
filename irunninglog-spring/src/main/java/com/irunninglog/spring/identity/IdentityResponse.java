package com.irunninglog.spring.identity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.identity.IIdentityResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class IdentityResponse extends AbstractResponse<Identity, IdentityResponse> implements IIdentityResponse<Identity, IdentityResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Identity.class)
    public Identity getBody() {
        return body();
    }

}
