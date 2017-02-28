package com.irunninglog.spring.profile;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetProfileResponse extends AbstractResponse<IProfile, GetProfileResponse> implements IGetProfileResponse<GetProfileResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Profile.class)
    public IProfile getBody() {
        return body();
    }

}
