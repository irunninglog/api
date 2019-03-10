package com.irunninglog.vertx.statistics;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.EndpointHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@EndpointHandler(endpoint = Endpoint.GET_STATISTICS)
public class GetStatisticsHandler extends AbstractRouteHandler {

    public GetStatisticsHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

    @Override
    protected void request(IRequest request, RoutingContext routingContext) {
        super.request(request, routingContext);

        String startDate = routingContext.request().getParam("startDate");
        String endDate = routingContext.request().getParam("endDate");

        logger.info("Getting statistics from {} to {}", startDate, endDate);

        request.getMap().put("startDate", startDate);
        request.getMap().put("endDate", endDate);
    }

}
