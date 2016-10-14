package com.irunninglog.vertx.routehandler;

import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.api.profile.ProfileRequest;
import com.irunninglog.api.profile.ProfileResponse;
import com.irunninglog.vertx.Address;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GetProfileHandler implements IRouteHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GetProfileHandler.class);

    private final Vertx vertx;

    public GetProfileHandler(Vertx vertx) {
        this.vertx = vertx;
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
    public void handle(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        LOG.info("Handling route {} {}", routingContext.currentRoute().getPath(), profileId);

        ProfileRequest request = new ProfileRequest().setId(Integer.parseInt(profileId));

        vertx.eventBus().<String>send(Address.Profile.getAddress(), Json.encode(request), result -> {
            if (result.succeeded()) {
                LOG.info("Got a result {}", result.result().body());

                ProfileResponse response = Json.decodeValue(result.result().body(), ProfileResponse.class);

                if (response.getStatus() == ResponseStatus.Ok) {
                    routingContext.request().response().setChunked(true).setStatusCode(response.getStatus().getCode()).write(Json.encode(response.getBody())).end();
                } else {
                    routingContext.request().response().setChunked(true).setStatusCode(response.getStatus().getCode()).write(response.getStatus().getMessage()).end();
                }
            } else {
                routingContext.request().response().setChunked(true).setStatusCode(ResponseStatus.Error.getCode()).write(ResponseStatus.Error.getMessage()).end();
            }
        });
    }

}
