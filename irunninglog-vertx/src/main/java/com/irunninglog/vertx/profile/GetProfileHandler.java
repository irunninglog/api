package com.irunninglog.vertx.profile;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.vertx.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.PROFILE_GET, request = IGetProfileRequest.class, response = IGetProfileResponse.class)
public final class GetProfileHandler extends AbstractProfileIdRouteHandler<IGetProfileRequest, IGetProfileResponse> {

    public GetProfileHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
