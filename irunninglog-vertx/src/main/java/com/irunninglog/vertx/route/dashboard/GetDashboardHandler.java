package com.irunninglog.vertx.route.dashboard;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.route.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.DASHBOARD_GET)
public final class GetDashboardHandler extends AbstractProfileIdRouteHandler<IGetDashboardRequest, IGetDashboardResponse> {

    public GetDashboardHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IGetDashboardRequest.class, IGetDashboardResponse.class);
    }

}
