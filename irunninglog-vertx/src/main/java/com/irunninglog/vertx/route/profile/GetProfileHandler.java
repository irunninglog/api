package com.irunninglog.vertx.route.profile;

import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.GetProfile)
public final class GetProfileHandler extends AbstractRouteHandler<ProfileRequest, ProfileResponse> {

    public GetProfileHandler(Vertx vertx) {
        super(vertx, ProfileResponse.class);
    }

    @Override
    protected ProfileRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("profile:get:{}", profileId);

        return new ProfileRequest().setId(Integer.parseInt(profileId));
    }

}
