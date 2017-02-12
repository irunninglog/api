package com.irunninglog.spring.profile;

import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetProfileResponse extends AbstractResponse<IProfile, GetProfileResponse> implements IGetProfileResponse<GetProfileResponse> {

}
