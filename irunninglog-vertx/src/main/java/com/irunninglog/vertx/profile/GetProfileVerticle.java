package com.irunninglog.vertx.profile;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractProfileIdEndpointVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.PROFILE_GET, request = IGetProfileRequest.class, response = IGetProfileResponse.class)
public final class GetProfileVerticle extends AbstractProfileIdEndpointVerticle<IGetProfileRequest, IGetProfileResponse> {

    private final IProfileService profileService;

    public GetProfileVerticle(IFactory factory, IMapper mapper, IProfileService profileService) {
        super(factory, mapper);

        this.profileService = profileService;
    }

    @Override
    protected void handle(IGetProfileRequest request, IGetProfileResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK).setBody(profileService.get(request.getProfileId()));
    }

    @Override
    protected boolean authorized(IUser user, IGetProfileRequest request) {
        return matches(user, request);
    }

}