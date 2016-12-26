package com.irunninglog.vertx.endpoint.dashboard;

import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.dashboard.IDashboardService;
import com.irunninglog.vertx.Address;
import com.irunninglog.vertx.endpoint.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.endpoint.EndpointConstructor;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle
public class DashboardVerticle extends AbstractRequestResponseVerticle<DashboardRequest, DashboardResponse> {

    private final IDashboardService service;

    @EndpointConstructor
    public DashboardVerticle(IDashboardService service) {
        super(DashboardRequest.class, DashboardResponse::new);

        this.service = service;
    }

    @Override
    protected DashboardResponse handle(DashboardRequest request) {
        return service.get(request);
    }

    @Override
    protected Address address() {
        return Address.DashboardGet;
    }

}
