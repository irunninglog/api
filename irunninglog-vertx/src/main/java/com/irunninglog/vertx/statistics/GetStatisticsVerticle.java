package com.irunninglog.vertx.statistics;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.GET_STATISTICS)
public class GetStatisticsVerticle extends AbstractRequestResponseVerticle {

    private final IStatisticsService service;

    public GetStatisticsVerticle(IFactory factory, IMapper mapper, IStatisticsService service) {
        super(factory, mapper);

        this.service = service;
    }

    @Override
    protected void handle(IRequest request, IResponse response) throws AuthnException {
        response.setStatus(ResponseStatus.OK).setBody(service.get(request.getUser(), request.getOffset()));
    }

}
