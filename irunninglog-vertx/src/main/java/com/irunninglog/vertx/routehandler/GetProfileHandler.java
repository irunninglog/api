package com.irunninglog.vertx.routehandler;

import com.irunninglog.api.profile.ProfileRequest;
import com.irunninglog.api.profile.ProfileResponse;
import com.irunninglog.vertx.Address;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public final class GetProfileHandler extends AbstactRouteHandler<ProfileResponse> {

    public GetProfileHandler(Vertx vertx) {
        super(vertx);
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
    protected ProfileResponse response(String body) {
        return Json.decodeValue(body, ProfileResponse.class);
    }

    @Override
    protected Address address() {
        return Address.ProfileGet;
    }

    @Override
    protected String request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("Got profile id {}", profileId);

        return Json.encode(new ProfileRequest().setId(Integer.parseInt(profileId)));
    }

}
