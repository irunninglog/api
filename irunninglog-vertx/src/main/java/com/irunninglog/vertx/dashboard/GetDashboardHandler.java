package com.irunninglog.vertx.dashboard;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.DASHBOARD_GET, request = IGetDashboardRequest.class, response = IGetDashboardResponse.class)
public final class GetDashboardHandler extends AbstractProfileIdRouteHandler<IGetDashboardRequest, IGetDashboardResponse> {

    public GetDashboardHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
