package com.irunninglog.vertx.report;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.REPORT_MILEAGE_RUN_GET)
public final class GetMileageByRunHandler extends AbstractGetReportHandler<IGetDataSetResponse> {

    public GetMileageByRunHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IGetDataSetResponse.class);
    }

}
