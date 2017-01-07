package com.irunninglog.vertx.route.dashboard;

import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.GetDashboard)
public final class GetDashboardHandler extends AbstractRouteHandler<DashboardRequest, DashboardResponse> {

    public GetDashboardHandler(Vertx vertx) {
        super(vertx, DashboardResponse.class);
    }

    @Override
    protected DashboardRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("dashboard:get:{}", profileId);

        return new DashboardRequest().setId(Integer.parseInt(profileId));
    }

}
