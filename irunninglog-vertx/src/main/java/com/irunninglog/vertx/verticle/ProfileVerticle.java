package com.irunninglog.vertx.verticle;

import com.irunninglog.profile.IProfileService;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.vertx.Address;

public final class ProfileVerticle extends AbstractRequestResponseVerticle<ProfileRequest, ProfileResponse> {

    private final IProfileService profileService;

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
