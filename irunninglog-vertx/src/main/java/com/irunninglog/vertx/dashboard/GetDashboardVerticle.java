package com.irunninglog.vertx.dashboard;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractProfileIdEndpointVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.DASHBOARD_GET, request = IGetDashboardRequest.class, response = IGetDashboardResponse.class)
public final class GetDashboardVerticle extends AbstractProfileIdEndpointVerticle<IGetDashboardRequest, IGetDashboardResponse> {

    private final IDashboardService service;

    public GetDashboardVerticle(IDashboardService service, IFactory factory, IMapper mapper) {
        super(factory, mapper);

        this.service = service;
    }

    @Override
    protected void handle(IGetDashboardRequest request, IGetDashboardResponse response) {
        //noinspection unchecked
        response.setStatus(ResponseStatus.OK)
                .setBody(service.get(request.getProfileId(), request.getOffset()));
    }

    @Override
    protected boolean authorized(IUser user, IGetDashboardRequest request) {
        return matches(user, request);
    }

}
