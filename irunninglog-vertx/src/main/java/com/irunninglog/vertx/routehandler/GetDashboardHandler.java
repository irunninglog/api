package com.irunninglog.vertx.routehandler;

import com.irunninglog.api.dashboard.DashboardRequest;
import com.irunninglog.api.dashboard.DashboardResponse;
import com.irunninglog.vertx.Address;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public class GetDashboardHandler extends AbstactRouteHandler<DashboardRequest, DashboardResponse> {

    public GetDashboardHandler(Vertx vertx) {
        super(vertx, DashboardResponse.class);
    }

    @Override
    public HttpMethod method() {
        return HttpMethod.GET;
    }

    @Override
    public String path() {
        return "/profiles/:profileid/dashboard";
    }

    @Override
    protected DashboardRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("dashboard:get:{}", profileId);

        return new DashboardRequest().setId(Integer.parseInt(profileId));
    }

    @Override
    protected Address address() {
        return Address.DashboardGet;
    }

}
