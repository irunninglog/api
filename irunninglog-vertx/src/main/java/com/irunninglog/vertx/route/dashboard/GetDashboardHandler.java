package com.irunninglog.vertx.route.dashboard;

import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.vertx.Address;
import com.irunninglog.vertx.route.AbstactRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(method = HttpMethod.GET, path = "/profiles/:profileid/dashboard")
public final class GetDashboardHandler extends AbstactRouteHandler<DashboardRequest, DashboardResponse> {

    public GetDashboardHandler(Vertx vertx) {
        super(vertx, DashboardResponse.class);
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
