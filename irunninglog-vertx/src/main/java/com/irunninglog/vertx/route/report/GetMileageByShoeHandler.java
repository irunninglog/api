package com.irunninglog.vertx.route.report;

import com.irunninglog.api.IFactory;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.api.Endpoint;
import com.irunninglog.vertx.route.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.GetMileageByShoe)
public final class GetMileageByShoeHandler extends AbstractGetReportHandler<IGetDataSetResponse> {

    public GetMileageByShoeHandler(Vertx vertx, IFactory factory) {
        super(vertx, factory, IGetDataSetResponse.class);
    }

}
