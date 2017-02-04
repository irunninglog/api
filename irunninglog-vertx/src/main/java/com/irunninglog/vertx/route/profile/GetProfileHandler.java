package com.irunninglog.vertx.route.profile;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.Endpoint;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetProfile)
public final class GetProfileHandler extends AbstractProfileIdRouteHandler<IGetProfileRequest, IGetProfileResponse> {

    public GetProfileHandler(Vertx vertx, IFactory factory) {
        super(vertx, factory, IGetProfileRequest.class, IGetProfileResponse.class);
    }

}
