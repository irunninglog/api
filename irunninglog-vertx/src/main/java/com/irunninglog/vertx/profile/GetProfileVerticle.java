package com.irunninglog.vertx.profile;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GET_PROFILE)
public final class GetProfileVerticle extends AbstractRequestResponseVerticle {

    private final IProfileService profileService;

    // Public for reflection
    @SuppressWarnings("WeakerAccess")
    public GetProfileVerticle(IFactory factory, IMapper mapper, IProfileService profileService) {
        super(factory, mapper);

        this.profileService = profileService;
    }

    @Override
    protected void handle(IRequest request, IResponse response) {
        response.setStatus(ResponseStatus.OK).setBody(profileService.get(request.getUser()));
    }

}
