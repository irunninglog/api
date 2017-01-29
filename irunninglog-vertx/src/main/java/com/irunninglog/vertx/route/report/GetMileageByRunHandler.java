package com.irunninglog.vertx.route.report;

import com.irunninglog.report.GetDataSetResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetMileageByRun)
public final class GetMileageByRunHandler extends AbstractGetReportHandler<GetDataSetResponse> {

    public GetMileageByRunHandler(Vertx vertx) {
        super(vertx, GetDataSetResponse.class);
    }

}
