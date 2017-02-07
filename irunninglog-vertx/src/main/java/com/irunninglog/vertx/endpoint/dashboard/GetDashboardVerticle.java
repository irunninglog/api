package com.irunninglog.vertx.endpoint.dashboard;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.endpoint.AbstractEndpointVerticle;
import com.irunninglog.vertx.endpoint.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GetDashboard)
public class GetDashboardVerticle extends AbstractEndpointVerticle<IGetDashboardRequest, IGetDashboardResponse> {

    private final IDashboardService service;

    public GetDashboardVerticle(IDashboardService service, IFactory factory, IMapper mapper) {
        super(factory, mapper, IGetDashboardRequest.class, IGetDashboardResponse.class);

        this.service = service;
    }

    @Override
    protected void handle(IGetDashboardRequest request, IGetDashboardResponse response) {
        response.setStatus(ResponseStatus.Ok)
                .setBody(service.get(request.getProfileId(), request.getOffset()));
    }

}
