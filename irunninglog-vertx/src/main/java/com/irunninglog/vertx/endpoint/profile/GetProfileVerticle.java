package com.irunninglog.vertx.endpoint.profile;

import com.irunninglog.profile.IProfileService;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetProfile)
public final class GetProfileVerticle extends AbstractEndpointVerticle<ProfileRequest, ProfileResponse> {

    private final IProfileService profileService;

    public GetProfileVerticle(IProfileService profileService) {
        super(ProfileRequest.class, ProfileResponse::new);
        this.profileService = profileService;
    }

    @Override
    protected ProfileResponse handle(ProfileRequest request) {
        return profileService.get(request);
    }

}
