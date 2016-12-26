package com.irunninglog.vertx.endpoint.profile;

import com.irunninglog.profile.IProfileService;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.vertx.Address;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.endpoint.EndpointConstructor;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle
public final class ProfileVerticle extends AbstractRequestResponseVerticle<ProfileRequest, ProfileResponse> {

    private final IProfileService profileService;

    @EndpointConstructor
    public ProfileVerticle(IProfileService profileService) {
        super(ProfileRequest.class, ProfileResponse::new);
        this.profileService = profileService;
    }

    @Override
    protected ProfileResponse handle(ProfileRequest request) {
        return profileService.get(request);
    }

    @Override
    protected Address address() {
        return Address.ProfileGet;
    }

}
