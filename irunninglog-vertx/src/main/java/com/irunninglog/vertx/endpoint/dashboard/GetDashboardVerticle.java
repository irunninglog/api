package com.irunninglog.vertx.endpoint.dashboard;

import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.dashboard.IDashboardService;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(
        endpoint = Endpoint.GetDashboard,
        constructorArgs = {IDashboardService.class})
public class GetDashboardVerticle extends AbstractEndpointVerticle<DashboardRequest, DashboardResponse> {

    private final IDashboardService service;

    public GetDashboardVerticle(IDashboardService service) {
        super(DashboardRequest.class, DashboardResponse::new);

        this.service = service;
    }

    @Override
    protected DashboardResponse handle(DashboardRequest request) {
        return service.get(request);
    }

}
