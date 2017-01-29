package com.irunninglog.vertx.route.report;

import com.irunninglog.report.GetDataSetResponse;
import com.irunninglog.service.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetMileageByShoe)
public final class GetMileageByShoeHandler extends AbstractGetReportHandler<GetDataSetResponse> {

    public GetMileageByShoeHandler(Vertx vertx) {
        super(vertx, GetDataSetResponse.class);
    }

}
