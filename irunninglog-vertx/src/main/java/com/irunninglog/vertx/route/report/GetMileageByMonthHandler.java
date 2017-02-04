package com.irunninglog.vertx.route.report;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetMileageByMonth)
public final class GetMileageByMonthHandler extends AbstractGetReportHandler<IGetMultiSetResponse> {

    public GetMileageByMonthHandler(Vertx vertx, IFactory factory) {
        super(vertx, factory, IGetMultiSetResponse.class);
    }

}
