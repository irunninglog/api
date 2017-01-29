package com.irunninglog.vertx.route.report;

import com.irunninglog.report.GetDataSetResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetMileageByRoute)
public final class GetMileageByRouteHandler extends AbstractGetReportHandler<GetDataSetResponse> {

    public GetMileageByRouteHandler(Vertx vertx) {
        super(vertx, GetDataSetResponse.class);
    }

}
