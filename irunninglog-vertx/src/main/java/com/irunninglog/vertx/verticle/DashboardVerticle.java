package com.irunninglog.vertx.verticle;

import com.irunninglog.api.dashboard.DashboardRequest;
import com.irunninglog.api.dashboard.DashboardResponse;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.vertx.Address;

public class DashboardVerticle extends AbstractRequestResponseVerticle<DashboardRequest, DashboardResponse> {

    private final IDashboardService service;

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
