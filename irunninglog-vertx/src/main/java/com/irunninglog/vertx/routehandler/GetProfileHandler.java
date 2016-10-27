package com.irunninglog.vertx.routehandler;

import com.irunninglog.api.profile.ProfileRequest;
import com.irunninglog.api.profile.ProfileResponse;
import com.irunninglog.vertx.Address;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public final class GetProfileHandler extends AbstactRouteHandler<ProfileRequest, ProfileResponse> {

    public GetProfileHandler(Vertx vertx) {
        super(vertx, ProfileResponse.class);
    }

    @Override
    public HttpMethod method() {
        return HttpMethod.GET;
    }

    @Override
    public String path() {
        return "/profiles/:profileid";
    }

    @Override
    protected Address address() {
        return Address.ProfileGet;
    }

    @Override
    protected ProfileRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("Got profile id {}", profileId);

        return new ProfileRequest().setId(Integer.parseInt(profileId));
    }

}
