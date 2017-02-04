package com.irunninglog.vertx.route.dashboard;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetDashboard)
public final class GetDashboardHandler extends AbstractProfileIdRouteHandler<IGetDashboardRequest, IGetDashboardResponse> {

    public GetDashboardHandler(Vertx vertx, IFactory factory) {
        super(vertx, factory, IGetDashboardRequest.class, IGetDashboardResponse.class);
    }

}
