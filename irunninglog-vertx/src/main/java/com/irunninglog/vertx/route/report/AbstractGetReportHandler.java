package com.irunninglog.vertx.route.report;

import com.irunninglog.report.GetReportRequest;
import com.irunninglog.service.AbstractResponse;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

abstract class AbstractGetReportHandler<T extends AbstractResponse> extends AbstractRouteHandler<GetReportRequest, T> {

    AbstractGetReportHandler(Vertx vertx, Class<T> responseClass) {
        super(vertx, responseClass);
    }

    @Override
    protected final GetReportRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("profile:get:{}", profileId);

        return new GetReportRequest().setId(Long.parseLong(profileId));
    }

}
