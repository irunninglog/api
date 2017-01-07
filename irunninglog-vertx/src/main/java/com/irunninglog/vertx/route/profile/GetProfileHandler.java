package com.irunninglog.vertx.route.profile;

import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.security.AccessControl;
import com.irunninglog.vertx.Address;
import com.irunninglog.vertx.route.AbstactRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(method = HttpMethod.GET,
        path = "/profiles/:profileid",
        address = Address.ProfileGet,
        access = AccessControl.AllowProfile)
public final class GetProfileHandler extends AbstactRouteHandler<ProfileRequest, ProfileResponse> {

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
