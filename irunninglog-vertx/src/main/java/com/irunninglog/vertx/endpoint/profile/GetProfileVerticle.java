package com.irunninglog.vertx.endpoint.profile;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetProfile)
public final class GetProfileVerticle extends AbstractEndpointVerticle<IGetProfileRequest, IGetProfileResponse> {

    private final IProfileService profileService;

    public GetProfileVerticle(IFactory factory, IProfileService profileService) {
        super(factory, IGetProfileRequest.class, IGetProfileResponse.class);
        this.profileService = profileService;
    }

    @Override
    protected void handle(IGetProfileRequest request, IGetProfileResponse response) {
        response.setStatus(ResponseStatus.Ok).setBody(profileService.get(request.getProfileId()));
    }

}
