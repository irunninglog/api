package com.irunninglog.vertx.endpoint.profile;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.PROFILE_GET)
public final class GetProfileVerticle extends AbstractEndpointVerticle<IGetProfileRequest, IGetProfileResponse> {

    private final IProfileService profileService;

    public GetProfileVerticle(IFactory factory, IMapper mapper, IProfileService profileService) {
        super(factory, mapper, IGetProfileRequest.class, IGetProfileResponse.class);

        this.profileService = profileService;
    }

    @Override
    protected void handle(IGetProfileRequest request, IGetProfileResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.Ok).setBody(profileService.get(request.getProfileId()));
    }

}
