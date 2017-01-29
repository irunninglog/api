package com.irunninglog.vertx.route.report;

import com.irunninglog.report.GetMultiSetResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetMileageByMonth)
public final class GetMileageByMonthHandler extends AbstractGetReportHandler<GetMultiSetResponse> {

    public GetMileageByMonthHandler(Vertx vertx) {
        super(vertx, GetMultiSetResponse.class);
    }

}
