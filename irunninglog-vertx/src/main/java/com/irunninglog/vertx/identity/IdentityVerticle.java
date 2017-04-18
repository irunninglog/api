package com.irunninglog.vertx.identity;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.identity.IIdentity;
import com.irunninglog.api.identity.IIdentityRequest;
import com.irunninglog.api.identity.IIdentityResponse;
import com.irunninglog.api.identity.IIdentityService;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.IDENTITY)
public final class IdentityVerticle extends AbstractRequestResponseVerticle<IIdentityRequest, IIdentityResponse> {

    private final IIdentityService identityService;

    public IdentityVerticle(IFactory factory, IMapper mapper, IIdentityService identityService) {
        super(factory, mapper, IIdentityRequest.class, IIdentityResponse.class);

        this.identityService = identityService;
    }

    @Override
    protected boolean authorized(IUser user, IIdentityRequest request) {
        return user != null;
    }

    @Override
    protected void handle(IIdentityRequest request, IIdentityResponse response) {
        IIdentity identity = identityService.identity(request.getUsername());

        //noinspection unchecked
        response.setBody(identity).setStatus(ResponseStatus.OK);
    }

}
