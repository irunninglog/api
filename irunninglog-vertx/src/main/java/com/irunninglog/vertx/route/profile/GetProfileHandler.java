package com.irunninglog.vertx.route.profile;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetProfile)
public final class GetProfileHandler extends AbstractProfileIdRouteHandler<IGetProfileRequest, IGetProfileResponse> {

    public GetProfileHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IGetProfileRequest.class, IGetProfileResponse.class);
    }

}
